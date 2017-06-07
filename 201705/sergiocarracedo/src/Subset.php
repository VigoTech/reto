<?php

namespace VigoJug;

class Subset {

  private $set;

  public function __construct($set) {
    $this->set = $set;
  }

  /*
   * Via: http://stackoverflow.com/a/6092999
   */
  private function getSubsets($in, $minLength = 1) {
    $count = count($in);
    $members = pow(2, $count);
    $return = array();
    for ($i = 0; $i < $members; $i++) {
      $b = sprintf("%0" . $count . "b", $i);
      $out = array();
      for ($j = 0; $j < $count; $j++) {
        if ($b{$j} == '1') {
          $out[] = $in[$j];
        }
      }
      if (count($out) >= $minLength) {
        $return[] = $out;
      }
    }
    return $return;
  }

  public function checkNulls() {
    $subsets = $this->getSubsets($this->set);

    foreach ($subsets as $subset) {
      if (empty($subset)) {
        continue;
      }
      $sum = array_sum($subset);

      if (empty($sum)) {
        return TRUE;
      }
    }

    return FALSE;
  }

  public function checkNulls2Items() {
    $subsets = $this->getSubsets($this->set);

    foreach ($subsets as $subset) {
      if (count($subset) != 2) {
        continue;
      }
      $sum = array_sum($subset);

      if (empty($sum)) {
        return TRUE;
      }
    }

    return FALSE;
  }
}