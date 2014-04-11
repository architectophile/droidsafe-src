#! /usr/bin/env php
<?php
$HOME = getenv ("HOME");
include "utils.php";

/**
 * merge_spec_info <info_file> <report_file>
 *
 *  Reads a file specifying spec/src/sink information for a set
 *  of calls.  The file is a spreadsheet saved as a tab-separated
 *  text.  The columns are:
 *
 *      person      - person who made the changes
 *      spec        - spec/safe/ban setting
 *      src/sink    - source/sink setting
 *      call        - signature of call
 *      comment     - comment about change
 *
 *  The info file is organized by class.  There is blank row
 *  between each class and at the top of the class there is an
 *  optional comment in the spec column.
 */

{
  $info_file = null;
  $print_classes_only = false;

  // Process arguments
  for ($i = 1; $i < count ($argv); $i++) {
    $arg = $argv[$i];
    if (($arg == "-debug") || ($arg == "-d"))
      $debug = true;
    else if ($arg == '-classes')
      $print_classes_only = true;
    else if (starts_with ($arg, "-"))
      usage_err ("Unexpected argument $arg");
    else
      $file = $arg;
  }

  // Process the info file and put all of the information
  // into info_map and bcomment
  $all_info = file ($info_file, FILE_IGNORE_NEW_LINES);
  foreach ($info as $all_info) {
    list ($person, $spec, $ss, $sig, $comment) = explode ("\t", $info);
    if (!$sig && !$spec) {
      assert (!$person && !$ss && !$comment, "bad format: $info");
      $block_comment = null;
      $new_block_comment = false;
      continue;
    }
    if (!$sig) {
      assert (!$person && !$ss && !$comment && $spec, "bad format: $info"); 
      $block_comment = $spec;
      $new_block_comment = true;
      continue;
    }
    
    // Figure out the class from the signature
    $class = preg_replace ("/:.*/", "", $sig);

    // If there was a block comment for this class, remember it
    if ($new_block_comment) {
      $bcomment[$class] = $block_comment;
      $new_block_comment = false;
    }

    // Remember the information for this signature
    if (!array_key_exists($sig)) 
      throw new Exception ("$sig appears twice: $info");
    $info_map[$sig] = $info;
  }

