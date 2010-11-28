<?php
/*------------------------------------------------------------------------------
    File: php/init.php
 Project: Bare PHP Framework
 Version: 0.1.0
      By: Tim Oram
 Purpose: The initialization script
------------------------------------------------------------------------------*/

/*| basic configuration |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|*/
// script start time with micro seconds
$_SERVER['SCRIPT_TIME_MICRO'] = microtime(true);

// include the configuration file
require 'config.php';

// add site's server path (root) to include path
set_include_path($cfg['project_root'] . PATH_SEPARATOR . $cfg['pear_path'] . PATH_SEPARATOR . $fcg['php_include_path_extra']);

// set default time zone
date_default_timezone_set($cfg['timezone']);

/*| class Loading |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|*/
// load these now because they are used in this file there will be a small speed
// increase from not using the autoloader
require 'php/classes/db/DB.class.php';
require 'php/classes/db/drivers/MySql.php';
require 'php/classes/ErrorHandler.class.php';
require 'php/classes/Session.class.php';
require $cfg['template_engine_path'];
require 'php/classes/TemplateEngine.class.php';

// setup the auto loader
function class_loader($class){
	global $cfg;
	// most classes
	if (file_exists($cfg['project_root'] . "php/classes/$class.class.php")) {
		require "php/classes/$class.class.php";
	}
	else if(substr($class, 0, 4) != 'Dwoo' && substr($class, -4) == 'Data'){
		require "php/dwoo/$class.php";
	}
	// the database classes
	else if(substr($class, 0, 2) == 'DB'){
		require 'php/sql/'. substr($class, 2) .'.sql.php';
	}
}
spl_autoload_register('class_loader');

/*| error and exception handling |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|*/

ErrorHandler::setAsHandler($cfg['error_level']);
ErrorHandler::setShowErrors($cfg['error_show']);
ErrorHandler::setMaxArgumentLength($cfg['error_max_arg_length']);
ErrorHandler::setMailSettings($cfg['error_mail'], $cfg['error_mail_subject'], $cfg['error_mail_to'], $cfg['error_mail_from']);
ErrorHandler::setLogSettings($cfg['error_log'], $cfg['error_log_type'], $cfg['error_log_destination']);
ErrorHandler::setFirebugSettings($cfg['error_firebug'], $cfg['error_firebug_collapse']);

/*| database |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|*/

try{
	DB::construct($cfg['db']['engine']);
	DB::connect($cfg['db']['host'], $cfg['db']['username'], $cfg['db']['password'], $cfg['db']['port']);
	DB::selectDB($cfg['db']['database']);
	DB::setPrefix($cfg['db']['table_prefix']);
	// i always unset the db password once it is not needed
	unset($cfg['db']['password']);
}
catch(DBError $e){
	trigger_error("Database Connection Error: " . $e->getMessage());
	echo "Database Connection Error. Reload the page to try again.";
	exit;
}

/*| template engine |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|*/
Session::init($cfg['guid']);
TemplateEngine::init($cfg['dwoo_template_directory'], $cfg['dwoo_compiled_directory'], $cfg['dwoo_cache_directory']);

                     
/*| magic quotes |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|*/
if(get_magic_quotes_gpc()) {
	function stripslashes_recurse($value){
		$value = is_array($value) ? array_map('stripslashes_recurse', $value) : stripslashes($value);
		return $value;
	}
	$_GET = array_map('stripslashes_recurse', $_GET);
	$_POST = array_map('stripslashes_recurse', $_POST);
	$_COOKIE = array_map('stripslashes_recurse', $_COOKIE);
}


/*| debug |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|*/

if($cfg['debug']){
	if(trim($cfg['firephp_path']) != ''){
		require_once $cfg['firephp_path'];
		// dump some common used info to firephp
		FB::dump('Get', $_GET);
		FB::dump('Post', $_POST);
		FB::dump('Cookie', $_COOKIE);
		if(isset($_SESSION)) FB::dump('Session', $_SESSION);
	}
}
