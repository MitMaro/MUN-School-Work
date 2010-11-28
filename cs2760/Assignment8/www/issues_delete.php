<?php
/*------------------------------------------------------------------------------
    File: www/issues_delete.php
 Project: cs2760 - Assignment #8
 Version: 0.1.0
      By: Lauren Stratton, Simon McIlhargey, Tim Oram
------------------------------------------------------------------------------*/
require 'init.php';

if(isset($_GET['id']) && (int)$_GET['id'] > 0){
	$id = (int)$_GET['id'];
	if(DBIssues::deleteIssue($id)){
		Session::addMessage('Issue Deleted');
	}
	else{
		Session::addMessage("Error Occurred");
	}
	header('Location: ' . $cfg['domain'] . $cfg['site_path'] . 'issues.php');		
}
else{
	header('Location: ' . $cfg['domain'] . $cfg['site_path'] . 'issues.php');
}