<?php
/*------------------------------------------------------------------------------
    File: www/issues_detail.php
 Project: cs2760 - Assignment #8
 Version: 0.1.0
      By: Lauren Stratton, Simon McIlhargey, Tim Oram
------------------------------------------------------------------------------*/
require 'init.php';

if(isset($_GET['id'])){
	$id = (int)$_GET['id'];
	$data = new IssuesDetailData($id);
	$tpl = TemplateEngine::templateFromFile('content/issues_detail');
}
else{
	header("Location: " . $cfg['domain'] . $cfg['site_path'] . 'issues.php');
}

TemplateEngine::output($tpl, $data);
