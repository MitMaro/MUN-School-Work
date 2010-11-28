<?php
/*------------------------------------------------------------------------------
    File: php/classes/ErrorHandler.class.php
 Project: Bare PHP Framework
 Version: 0.1.0
      By: Tim Oram
 Purpose: The Error Handler
 Depends: Firebug (optional)
------------------------------------------------------------------------------*/
class ErrorHandler {
	
	private $errors;
	
	private static $instance;
	
	// error handler settings
	private static $show = false;
	private static $max_arg_length = 0;
	private static $mail = false;
	private static $mail_subject = '';
	private static $mail_to = '';
	private static $mail_from = '';
	private static $log = false;
	private static $log_destination = '';
	private static $log_type = 0;
	private static $firebug = false;
	private static $firebug_collapsed = false;
	
	const LOG_SYSTEM = 0;
	const LOG_FILE = 3;
	const LOG_SAPI = 4;
	
	private static $error_lookup = array (
		E_ERROR => 'Error',
		E_WARNING => 'Warning',
		E_PARSE => 'Parsing Error',
		E_NOTICE => 'Notice',
		E_CORE_ERROR => 'Core Error',
		E_CORE_WARNING => 'Core Warning',
		E_COMPILE_ERROR => 'Compile Error',
		E_COMPILE_WARNING => 'Compile Warning',
		E_USER_ERROR => 'User Error',
		E_USER_WARNING => 'User Warning',
		E_USER_NOTICE => 'User Notice',
		E_STRICT => 'Strict Notice',
		E_RECOVERABLE_ERROR => 'Catchable Fatal Error',
		E_DEPRECATED => 'Deprecated',
		E_USER_DEPRECATED => 'User Deprecated'
	);
	
	private function __construct(){}
	
	public static function setAsHandler($error_level = E_ALL){
		if(!self::$instance instanceof ErrorHandler){
			self::$instance = new ErrorHandler();
			set_error_handler(array('ErrorHandler', 'error'), $error_level);
			set_exception_handler(array('ErrorHandler', 'exception'));
		}
	}
	
	public function __destruct(){
		try{
			if(count(self::$instance->errors) > 0){
				if(self::$mail){
					self::sendToMail();
				}
				if(self::$firebug){
					self::sendToFirebug();
				}
			}
		}
		catch(Exception $e){
			echo "Uncaught Exception in Deconstructor. " . $e->getMessage();
		}
	}
	
	public static function setShowErrors($show){
		self::$show = $show;
	}

	public static function setMaxArgumentLength($length){
		self::$max_arg_length = $length;
	}
	
	public static function setMailSettings($use, $subject = 'Error Report', $to = '', $from = ''){
		self::$mail = $use;
		self::$mail_subject = $subject;
		self::$mail_to = $to;
		self::$mail_from = $from;
	}
	
	public static function setLogSettings($use, $type = ErrorHandler::LOG_SYSTEM, $destination = 'php_error.log'){
		self::$log = $use;
		self::$log_type = $type;
		self::$log_destination = $destination;
	}
	
	public static function setFirebugSettings($use, $collapse = false){
		self::$firebug = $use;
		self::$firebug_collapsed = $collapse;
	}
	
	public static function error($errno, $errmsg, $filename, $linenum){

		// capture suppressed errors
		if(error_reporting() == 0){
			return;
		}
		
		$traces = array();
		
		$backtrace = debug_backtrace();
		
		// catch invalid errors
		if($backtrace > 2){
			// remove the first part of the trace as it is this function
			unset($backtrace[0]);
			self::processError($errno, $errmsg, $filename, $linenum, $backtrace);
		}
	}
		
	public static function exception($e){
		try{
			self::processError($e->getCode(), $e->getMessage(), $e->getFile(), $e->getLine(), $e->getTrace(), get_class($e));
		}
		catch (Exception $e){
        	trigger_error(get_class($e) . ' thrown within the exception handler.' . 
        	             'Message: ' . $e->getMessage() . ' on line ' . $e->getLine(), E_USER_WARNING);
		}
	}
	
	

	private static function processError($errno, $errmsg, $filename, $linenum, $backtrace, $exception = false){

		$traces = array();
		
		foreach($backtrace as $trace){
			// no need to include the call to trigger error if there was one
			if(isset($trace['function']) && $trace['function'] != 'trigger_error'){
				isset($trace['file'])? null : $trace['file'] = '';
				isset($trace['line'])? null : $trace['line'] = '-';
				isset($trace['class'])? null : $trace['class'] = '';
				isset($trace['type'])? null : $trace['type'] = '';
				isset($trace['args'])? null : $trace['args'] = array();
				foreach($trace['args'] as $k => $arg){
					if(is_string($arg) && strlen(trim($arg)) > self::$max_arg_length + 3){
						$trace['args'][$k] = substr(trim($arg), 0 , self::$max_arg_length) . "...";
					}
					elseif(is_object($arg)){
						$trace['args'][$k] = 'Object[' . get_class($arg) . ']';
					}
					else{
						$trace['args'][$k] = (string)$arg;
					}
				}
				$traces[] = $trace;
			}
		}
		self::$instance->errors[] = array(
			'number' => $errno,
			'message' => $errmsg,
			'file' => $filename,
			'line' => $linenum,
			'trace' => $traces,
			'exception' => $exception
		);

		if(self::$show){
			self::sendLastError();
		}
		if(self::$log){
			self::sendLastToLog();
		}
	}

	private static function sendLastError(){
		$err = end(self::$instance->errors);
		// the below styles should make the error appear even when they happen in
		// the middle of other html
		echo '<p style="font-family:monospace;font-size:14px;float:left;color:#000;background:#fff;width:100%;text-align:center;">';
		if($err['exception'] === false){
			echo 'A PHP error has occured in <b>' . $err['file'] . '</b> on line ';
			echo '<b>' . $err['line'] . '</b> @ <b>' . date('g:i:s a') . "</b><br/>";
			echo '<b>' . self::$error_lookup[$err['number']] . ':</b> ' . $err['message'];
		}
		else{
			echo 'An uncaught exception has occured in <b>' . $err['file'] . '</b> on line ';
			echo '<b>' . $err['line'] . '</b> @ <b>' . date('g:i:s a') . "</b><br/>";
			echo '<b>' . $err['exception'] . '[' . $err['number'] . ']:</b> ' . $err['message'];
		}
		if(count($err['trace']) > 0){
			echo "<br/><b>Trace:</b><span style='font-size:12px;color:#666666;'><br/>";
			foreach($err['trace'] as $t){
				echo '&nbsp;&nbsp;&nbsp;&nbsp;' . $t['class'] . $t['type'] . $t['function'];
				echo '(' . implode(', ', $t['args']) . ')';
				echo  ' in file <b>' . $t['file'] . '</b> on line: <b>' . $t['line'] ."</b><br/>";
			}
			echo '</span>';
		}
		echo "</p>";
	}
	
	private static function sendToMail(){
		$headers  = 'MIME-Version: 1.0' . "\r\n";
		$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
		$headers .= 'From: ' . self::$mail_from . "\r\n";
		$message = '<p style="font-family:monospace;font-size:14px;">';
		$message .= 'Error report mailed on: ' . date('M j, Y g:i:s a') . '<br/>';
		$message .= 'Number of errors: ' . count(self::$instance->errors) . "<br/><br/>";
		$message .= '<hr style="border:0;height:1px;background:#666;"/>';
		foreach(self::$instance->errors as $err){
			if($err['exception'] === false){
				$message .= 'A PHP error has occured in <b>' . $err['file'] . '</b> on line <b>' . $err['line'] . '</b><br/>';
				$message .= '<b>' . self::$error_lookup[$err['number']] . ':</b> ' . $err['message'];
			}
			else{
				$message .= 'An uncaught exception has occured in <b>' . $err['file'] . '</b> on line <b>' . $err['line'] . '</b><br/>';
				$message .= '<b>' . $err['exception'] . '[' . $err['number'] . ']:</b> ' . $err['message'];
			}
			if(count($err['trace']) > 0){
				$message .= "<br/><b>Trace:</b><span style='font-size:12px;color:#666666;'><br/>";
				foreach($err['trace'] as $t){
					$message .= '&nbsp;&nbsp;&nbsp;&nbsp;' . $t['class'] . $t['type'] . $t['function'];
					$message .= '(' . implode(', ', $t['args']) . ')';
					$message .= ' in file <b>' . $t['file'] . '</b> on line: <b>' . $t['line'] ."</b><br/>";
				}
				$message .= '</span>';
			}
			$message .= '<hr style="border:0;height:1px;background:#666;"/>';
		}
		$message .='</p>';
		mail(self::$mail_to, self::$mail_subject, $message, $headers);
	}
	
	private static function sendToFirebug(){
		$num_errors = count(self::$instance->errors);
		if($num_errors == 1){
			$msg = 'A PHP error have occured @ ' . date('g:i:s a');
		}
		else{
			$msg = $num_errors . ' PHP errors have occured @ ' . date('g:i:s a');
		}

		FB::group($msg, array('Collapsed' => self::$firebug_collapsed));
		foreach(self::$instance->errors as $err){
			// some processing needs to be done to the errmsg because it can
			// at times contain html, which we don't want in firebug.
			if($err['exception'] === false){
				FB::error(self::$error_lookup[$err['number']] . ': ' .
				          strip_tags(html_entity_decode($err['message'], ENT_QUOTES)) .
				          ': ' . $err['file'] . ' on line: ' . $err['line']);
			}
			else{
				FB::error('Uncaught Exception: ' . $err['exception'] . '[' . $err['number'] . ']: ' . 
				          strip_tags(html_entity_decode($err['message'], ENT_QUOTES)) .
				          ': ' . $err['file'] . ' on line: ' . $err['line']);
			}
			if(count($err['trace']) > 0){
				FB::log("Trace:");
				foreach($err['trace'] as $i => $t){
					FB::log($t['class'] . $t['type'] . $t['function'] . '(' . implode(', ', $t['args']) . ') in file ' . 
					        $t['file'] . ' on line: ' . $t['line'], $i + 1);
				}
			}
			FB::log("");
		}
		FB::groupEnd();
	}
	
	private static function sendLastToLog(){
		$err = end(self::$instance->errors);
		$log = array();
 		$log[] = '[' . date('D M d H:i:s Y') . '] [' . self::$error_lookup[$err['number']] . '] ' . $err['message'] .
		         ': ' . $err['file'] . ' on line: ' . $err['line'] . "\n";
		foreach($err['trace'] as $i => $t){
			$log[] =  (($i == 0) ? 'Trace: ' : '       ') . $t['class'] . $t['type'] . $t['function'] .
			         '(' . implode(', ', $t['args']) . ') in file ' . $t['file'] . ' on line: ' . $t['line'] . "\n";
		}
		foreach($log as $l){
			error_log($l, self::$log_type, self::$log_destination);
		}
	}
}
