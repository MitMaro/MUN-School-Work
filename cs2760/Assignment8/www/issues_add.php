<?php
/*------------------------------------------------------------------------------
    File: www/issues_add.php
 Project: cs2760 - Assignment #8
 Version: 0.1.0
      By: Lauren Stratton, Simon McIlhargey, Tim Oram
------------------------------------------------------------------------------*/
require 'init.php';

if(isset($_POST['name'], $_POST['details'])){
	$name = htmlentities($_POST['name'], ENT_QUOTES);
	$details = nl2br($_POST['details']);
	
	$author = 2;
	
	if(isset($_POST['type']) && (int)$_POST['type'] >= 0){
		$type = (int)$_POST['type'];
	}
	else{
		$type = 1;
	}
	
	if(isset($_POST['target']) && (int)$_POST['target'] >= 0){
		$target = (int)$_POST['target'];
	}
	else{
		$target = 1;
	}
		
	if(isset($_POST['assign']) && (int)$_POST['assign'] >= 0){
		$assign = (int)$_POST['assign'];
	}
	else{
		$assign = 1;
	}
	
	if(isset($_POST['status']) && (int)$_POST['status'] == 'closed'){
		$status = 'closed';
	}
	else{
		$status = 'open';
	}
	
	if(isset($_POST['due'])){
		$due = $_POST['due'];
	}
	else{
		$due = date("Y-m-d");
	}
	
	
	$added = date("Y-m-d");
	
	if(DBIssues::addIssue($type, $name, $details, $target, $assign, $due, $added, $added, $status, $author)){
		Session::addMessage('Issue Added Successfully');
		header("Location: " . $cfg['domain'] . $cfg['site_path'] . 'issues_detail.php?id=' . DB::lastInsertedId());
	}
	else{
		Session::addMessage("An Error Occurred");
		header("Location: " . $cfg['domain'] . $cfg['site_path'] . 'issues_add.php');
	}
	exit;
}


$tpl = TemplateEngine::templateFromFile('content/issues_add');

TemplateEngine::output($tpl, new IssuesData());
