<?php
/*------------------------------------------------------------------------------
    File: php/classes/DB_Drivers/MySql.php
 Project: Bare PHP Framework
 Version: 0.1.0
      By: Tim Oram
 Purpose: The MySql driver for DB
 Depends: DB
------------------------------------------------------------------------------*/

class MySql_DBDriver Extends DBDriver {

	// connects to the MySql database
	public function connect($host = 'localhost', $user = null, $pass = null, $port = 3306){
		$this->res = mysql_connect($host . ':' . $port, $user, $pass);
		if($this->res == false){
			$this->connectError();
		}
	}

	// select a database
	public function selectDB($db_name){
		if(!mysql_select_db($db_name, $this->res)){
			$this->error();	
		}
		return true;
	}

	// returns a string that is safe to use in the database
	public function makeSafe(&$str){
		$str = mysql_real_escape_string($str, $this->res);
		if($str === false){
			$this->error();
		}
		$this->calls_count++;
	}
	
	// performs a MySQL query and returns the result
	public function query($qs){
		$result = mysql_query($qs, $this->res);
		if($result === false){
			$this->error();
		}
		$this->calls_count++;
		return $result;
	}

	// returns a row of a mysql query
	public function fetchRow($qs){
		$result = $this->query($qs);
		$return = mysql_fetch_assoc($result);
		if($return === false){
			$return = array();
		}
		mysql_free_result($result);
		return $return;
	}

	// returns all rows of a mysql query
	public function fetchAll($qs){
		$result = $this->query($qs);

		$return = array();

		while ($d = mysql_fetch_assoc($result)){
			$return[] = $d;
		}

		mysql_free_result($result);

		return $return;
	}

	// returns the last inserted id
	public function lastInsertedId(){
		$this->calls_count++;
		$result = mysql_insert_id($this->res);
		if($result === false){
			$this->error();
		}
		return $result;
	}

	// set and throw a MySQL formatted error
	protected function error(){
		// if there is a connection then get the error from the server
		if(!is_null($this->res)) {
			$msg = mysql_error($this->res);
			$num = mysql_errno($this->res);
			$this->setError($msg, $num);
		}
		// no connection so lets try to get some info
		else {
			$msg = mysql_error();
			$num = mysql_errno();
			$this->setError($msg, $num);
		}
		$this->throwError($msg, $num);
	}

}
