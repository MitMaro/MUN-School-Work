<?php
/*------------------------------------------------------------------------------
    File: php/classes/Session.class.php
 Project: Bare PHP Framework
 Version: 0.1.0
      By: Tim Oram
 Purpose: Various methods for handling sessions.
------------------------------------------------------------------------------*/

class Session {

	// holds the one time variables before they are placed in the session
	private $once_variables;
	
	// the instance of this class
	private static $instance;
	
	// the application GGUID for use in making the session unique
	private static $guid;
	
	// this is a singleton like class
	private function __construct(){}

	 // initiate session, this will start the session and create a session
	 // object as well as perform any other initilizing task.
	public static function init($guid = null){
		if(!self::$instance instanceof Session){
			session_start();
			self::$instance = new Session();
			
			if($guid != null && $guid != ''){
				self::$guid = $guid;
			}
			else{
				self::$guid = "no_guid_set";
				trigger_error('No GUID set for Session class', E_USER_NOTICE);
			}
		}
	}
	

	// reset the application session
	public static function reset(){
		$_SESSION[self::$guid] = array();		
	}

	// sets a one time variable, this variable will stick around for one extra
	// session only (ie. the next page view)
	public static function setOnce($key, $value){
		self::init();
		self::$instance->once_variables[$key] = $value;
	}

	// gets a variable saved as a one time variable in the previous script run
	public static function getOnce($key, $default = NULL){
		self::init();
		// handle default values
		if(!is_null($default)
		   && !isset($_SESSION[self::$guid]['__once__'][$key])){
			return $default;
		}
		return $_SESSION[self::$guid]['__once__'][$key];
	}
	
	// pushes this sessions once variables over to the next session
	public static function pushOnce(){
		self::init();
		if(isset($_SESSION[self::$guid]['__once__'])){
			foreach($_SESSION[self::$guid]['__once__'] as $key => $value){
				self::setOnce($key, $value);
			}
		}
	}
	
	// add to the special once variables for messages
	public static function addMessage($message){
		self::init();
		if(!isset(self::$instance->once_variables['__messages__'])){
			self::$instance->once_variables['__messages__'] = array();
		}
		self::$instance->once_variables['__messages__'][] = $message;
	}
	
	// return the list of messages
	public static function getMessages(){
		self::init();
		if(!isset($_SESSION[self::$guid]['__once__']['__messages__'])){
			return array();
		}
		return $_SESSION[self::$guid]['__once__']['__messages__'];
	}
	
	// sets a user setting, this can be used for saving individual page settings
	// and preferences. The $id is for grouping settings, passing a page id is
	// a good example. These settings will stay around till the session is 
	// destroyed or reset using the resetSettings method.
	public static function setSetting($id, $key, $value){
		self::init();
		$_SESSION[self::$guid]['__settings__'][$id][$key] = $value;
	}
	
	// get a previously stored user setting
	public static function getSetting($id, $key, $default = NULL){
		self::init();
		// default value handling
		if(!is_null($default)
		   && !isset($_SESSION[self::$guid]['__settings__'][$id][$key])){
			$_SESSION[self::$guid]['__settings__'][$id][$key] = $default;
			return $default;
		}
		return $_SESSION[self::$guid]['__settings__'][$id][$key];
	}
	
	// reset all the settings or a group of settings
	public static function resetSettings($page_id = NULL){
		self::init();
		// if a page id was given reset only that page
		if(!is_null($page_id)){
			$_SESSION[self::$guid]['__settings__'][$page_id] = array();
		}
		// if a page id was not give reset all page settings
		else{
			$_SESSION[self::$guid]['__settings__'] = array();
		}
	}
	
	// sets a user variable, this is for login user information
	public static function setUser($key, $value = null){
		self::init();
		if(is_array($key)){
			foreach($values as $key => $value){
				$_SESSION[self::$guid]['__user__'][$key] = $value;
			}
		}
		else{
			$_SESSION[self::$guid]['__user__'][$key] = $value;
		}
	}
	
	// returns a user varaible
	public static function getUser($key, $default = null){
		self::init();
		if(!isset($_SESSION[self::$guid]['__user__'][$key])){
			return $default;
		}
		return $_SESSION[self::$guid]['__user__'][$key];
	}
	
	// reset user
	public static function resetUser(){
		self::init();
		$_SESSION[self::$guid]['__user__'] = array();
	}
	
	// returns all the user variables
	public static function getUserAll(){
		self::init();
		if(isset($_SESSION[self::$guid]['__user__'])
		   && is_array($_SESSION[self::$guid]['__user__'])){
			return $_SESSION[self::$guid]['__user__'];
		}
		else{
			return array();
		}
	}
	
	// run some clean up at the end of the script
	public function __destruct(){
		$_SESSION[self::$guid]['__once__'] = self::$instance->once_variables;
	}	
}

