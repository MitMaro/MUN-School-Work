<?php
/*------------------------------------------------------------------------------
    File: php/dwoo/IssuesListData.php
 Project: cs2760 - Assignment #8
 Version: 0.1.0
      By: Lauren Stratton, Simon McIlhargey, Tim Oram
------------------------------------------------------------------------------*/

class IssuesListData extends IssuesData {
		
	public function __construct(){
		parent::__construct();
		
		$this->assign('issues', DBIssues::getIssues());
	}
	
}
