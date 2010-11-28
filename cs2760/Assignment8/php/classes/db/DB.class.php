<?php
/*------------------------------------------------------------------------------
    File: php/classes/DB.class.php
 Project: Bare PHP Framework
 Version: 0.1.0
      By: Tim Oram
 Purpose: The Database handling class, a driver is required for this class to
          work.
------------------------------------------------------------------------------*/

class DB {

	// the last database connection
	private static $res = null;
	
	// the database driver to use
	private static $driver = 'MySql_DBDriver';
	
	// this is a static only class
	private function __construct(){}
	
	// makes and returns a database driver
	public static function construct($driver) {
		if(!class_exists($driver . '_DBDriver')){
			require 'drivers/' . $driver . '.php';
		}
		
		$driver = $driver . '_DBDriver';
		self::$res = new $driver;
		return self::$res;
	}
	
	// returns the last driver constructed
	public static function getDriver(){
		return self::$res;
	} 
	
	// set the table prefix
	public static function setPrefix($prefix){
		self::$res->prefix = $prefix;
	}
	
	// get the table prefix
	public static function getPrefix(){
		return self::$res->prefix;
	} 

	// connect to the database
	static public function connect(){
		$args = func_get_args(); // have to do this because of a stupid decision
		                         // by the php team
		// call this way because of the variable parameters of the connection
		// method
		call_user_func_array(array(self::$res, 'connect'), $args);
	}

	 // select a database
	static public function selectDB($db_name){
		return self::$res->selectDB($db_name);
	}

	// returns a string that is safe to use in the database
	static public function makeSafe(&$str){
		self::$res->makeSafe($str);
	}
	
	
	// performs a query and returns the result
	static public function query($qs){
		return self::$res->query($qs);
	}

	// returns a row of a query
	static public function fetchRow($qs){
		return self::$res->fetchRow($qs);
	}

	// return all rows of a query
	static public function fetchAll($qs){
		return self::$res->fetchAll($qs);
	}

	// returns the last inserted id
	static public function lastInsertedId(){
		return self::$res->lastInsertedId();
	}
	
	// returns the last error
	static final public function lastError(){
		return self::$res->lastError();
	}

	// returns the count of DB calls made
	static public function getNumberOfQueries(){
		return self::$res->getNumberOfQueries();
	}
}

abstract class DBDriver {

	protected $calls_count = 0;
	protected $last_error = array('message' => 'No Error', 'code' => 0);
	
	// the database connection resource
	protected $res;

	// the prefix to attach to table names
	public $prefix = '';
		
	// connect to the database
	public abstract function connect();
	 // select a database
	public abstract function selectDB($db_name);
	// returns a string that is safe to use in the database
	public abstract function makeSafe(&$str);
	// performs a query and returns the result
	public abstract function query($qs);
	// returns a row of a query
	public abstract function fetchRow($qs);
	// return all rows of a query
	public abstract function fetchAll($qs);
	// returns the last inserted id
	public abstract function lastInsertedId();
	// the error function
	protected abstract function error();
	
	 // returns the last error
	public final function lastError(){
		return $this->last_error;
	}

	// returns the count of DB calls made
	public function getNumberOfQueries(){
		return $this->num_calls;
	}
	
	// throw a formatted DB Error
	protected function throwError($message, $code){
		throw new DBError("Database Error\n  Message [" . $code ."]: " . $message, $code);
	}

	// throw a DB connection error
	protected function connectError(){
		throw new DBConnectionError("Error Connecting to Database");
	}
	
	// sets the error information
	protected function setError($message, $code){
		$this->last_error = array('message' => $message, 'code' => $code);
	}
	
}


class DBError extends Exception {}
class DBConnectionError extends DBError {}
class DBSelectionError extends DBError {}
