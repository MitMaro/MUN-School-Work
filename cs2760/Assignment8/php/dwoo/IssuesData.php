<?php
/*------------------------------------------------------------------------------
    File: php/dwoo/IssuesData.php
 Project: cs2760 - Assignment #8
 Version: 0.1.0
      By: Lauren Stratton, Simon McIlhargey, Tim Oram
------------------------------------------------------------------------------*/

class IssuesData extends BaseData {
		
	public function __construct(){
		parent::__construct();
		
		$this->assign('developers', array(
			'Developer 1',
			'Developer 2',
			'Developer 3',
			'Developer 4',
			'Developer 5',
			'Developer 6'
		));
		
		$this->assign('trackers', array(
			'Bug',
			'Feature',
			'Documentation'
		));
		
		$this->assign('versions', array(
			'SLBP 0.1.0',
			'SLBP 0.1.1',
			'SLBP 0.1.2',
			'SLBP 0.2.0',
			'SLBP 0.2.1',
			'SLBP 0.3.0'
		));
	}
	
}
