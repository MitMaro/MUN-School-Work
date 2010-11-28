<?php
/*------------------------------------------------------------------------------
    File: www/issues_edit.php
 Project: cs2760 - Assignment #8
 Version: 0.1.0
      By: Lauren Stratton, Simon McIlhargey, Tim Oram
------------------------------------------------------------------------------*/
require 'init.php';

$tpl = TemplateEngine::templateFromFile('content/issues_edit');

if(isset($_GET['id']) && (int)$_GET['id'] > 0){
	$id = (int)$_GET['id'];
}
else if(isset($_POST['id'], $_POST['name'], $_POST['details'])){
	$id = (int)$_POST['id'];
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
	
	if(DBIssues::editIssue($id, $type, $name, $details, $target, $assign, $due, $added, $added, $status, $author)){
		Session::addMessage('Issue Edited Successfully');
		header("Location: " . $cfg['domain'] . $cfg['site_path'] . 'issues_detail.php?id=' . $id);
	}
	else{
		Session::addMessage("An Error Occurred");
		header("Location: " . $cfg['domain'] . $cfg['site_path'] . 'issues_add.php');
	}
	exit;
}
else{
	header('Location: ' . $cfg['domain'] . $cfg['site_path'] . 'issues.php');
	exit;
}

TemplateEngine::output($tpl, new IssuesEditData($id));

