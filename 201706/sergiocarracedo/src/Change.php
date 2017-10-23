<?php


namespace VigoJugCoins;

class Change {
  private $coins = [];

  public function __construct($coinsValues, $coinsAmounts) {

    $this->coins = array_combine($coinsValues, $coinsAmounts);
    krsort($this->coins);

  }

  public function getChange($change) {
    $coins = [];
    $remain = $change;


    foreach ($this->coins as $value => $amount) {
      $coinsNeeded = intdiv($remain, $value);
      if ($coinsNeeded < 0) {
        $coinsNeeded = 0;
      }
      if ($amount == -1) {
        $coinsUsed = $coinsNeeded;
      } else {
        $coinsUsed = min($coinsNeeded, $amount);
      }

      $coins[$value] = $coinsUsed;

      $remain = $remain - ($coinsUsed * $value);
      if ($remain == 0) {
        break;
      }
    }

    if ($remain != 0) {
      return false;
    }
    return $coins;
  }


}