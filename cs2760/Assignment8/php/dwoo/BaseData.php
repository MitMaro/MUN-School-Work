<?php
/*------------------------------------------------------------------------------
    File: php/dwoo/BaseData.php
 Project: Bare PHP Framework
 Version: 0.1.0
      By: Tim Oram
------------------------------------------------------------------------------*/

class BaseData extends Dwoo_Data {
	public function __construct(){
		$this->assign('request', array(
			'action' => Session::getOnce('action', false),
			'status' => Session::getOnce('status', false),
			'data' => Session::getOnce('data', false)
		));
		$this->assign('site', array(
			'messages' => Session::getMessages()
		));
	}
	
	public function append($name, $val = null, $merge = false){
		if(is_array($val)){
			foreach($val as $k => $v){
				$this->data[$name][$k] = $v;
			}
		}
		else{
			parent::append($name, $val, $merge);
		}
	}
}
