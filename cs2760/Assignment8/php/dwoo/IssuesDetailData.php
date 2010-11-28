<?php
/*------------------------------------------------------------------------------
    File: php/dwoo/IssuesDetailData.php
 Project: cs2760 - Assignment #8
 Version: 0.1.0
      By: Lauren Stratton, Simon McIlhargey, Tim Oram
------------------------------------------------------------------------------*/

class IssuesDetailData extends IssuesData {
		
	public function __construct($id){
		parent::__construct();
		
		$this->assign('issue', DBIssues::getIssueDetail($id));
		
	}
	
}
