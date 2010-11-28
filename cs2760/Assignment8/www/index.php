<?php
/*------------------------------------------------------------------------------
    File: www/index.php
 Project: cs2760 - Assignment #8
 Version: 0.1.0
      By: Lauren Stratton, Simon McIlhargey, Tim Oram
------------------------------------------------------------------------------*/
require 'init.php';

$tpl = TemplateEngine::templateFromFile('content/projects');

TemplateEngine::output($tpl);
