<?php
/*------------------------------------------------------------------------------
    File: php/sql/Issues.sql.php
 Project: cs2760 - Assignment #8
 Version: 0.1.0
      By: Lauren Stratton, Simon McIlhargey, Tim Oram
------------------------------------------------------------------------------*/

class DBIssues {
	
	public static function getIssues(){
		$qs = 'SELECT`id`,`name`,`author_id`,`tracker_id`, `date_due` FROM`' . DB::getPrefix() . 'issues`';
		return DB::fetchAll($qs);
	}
	
	public static function getIssueDetail($id){
		$qs = 'SELECT`id`, `tracker_id`, `name`, `details`, `target_version_id`, `assigned_to`,'.
			'`date_due`, `date_added`, `date_edited`, `status`, `author_id` FROM`' . DB::getPrefix() . 'issues`'.
			"WHERE `id` = '$id'";
		return DB::fetchRow($qs);
	}
	
	public static function deleteIssue($id){
		$qs = 'DELETE FROM `' . DB::getPrefix() . 'issues`' .
			"WHERE `id` = '$id'";
		return DB::query($qs);
	}
	
	public static function addIssue($tracker, $name, $details, $target, $assigned, $due, $added, $added, $status, $author){
		$qs = 'INSERT INTO `' . DB::getPrefix() . 'issues`'.
			'(`tracker_id`, `name`, `details`, `target_version_id`, `assigned_to`, `date_due`,'.
			 '`date_added`, `date_edited`, `status`, `author_id`)'.
			"VALUES ('$tracker', '$name', '$details', '$target', '$assigned', '$due', '$added', '$added', '$status', '$author')";
		return DB::query($qs);
	}

	public static function editIssue($id, $tracker, $name, $details, $target, $assigned, $due, $added, $added, $status, $author){
		$qs = 'UPDATE `' . DB::getPrefix() . 'issues`'.
			"SET `tracker_id` = '$tracker', `name` = '$name', `details` = '$details', `target_version_id` = '$target', `assigned_to` = '$assigned',".
			"`date_due` = '$due', `date_added` = '$added', `date_edited` = '$added', `status` = '$status', `author_id` = '$author'".
			"WHERE `id` = '$id'";
		return DB::query($qs);
	}
	
}