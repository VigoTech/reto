<?php

namespace SubsetSum;

class BruteForce
{
    private $list;

    public function __construct($list)
    {
        $this->list = $list;
    }

    public function getSubsets($array, $value = 0)
    {
        sort($array, SORT_NUMERIC);

        $results = [[]];

        foreach ($array as $element) {
            foreach ($results as $combination) {
                $candidate = array_merge(array($element), $combination);

                if (array_sum($candidate) <= $value) {
                    $results[] = $candidate;
                }
            }
        }

        return $results;
    }


    public function existsSum($value = 0)
    {
        if (in_array($value, $this->list, true)) {
            return true;
        }

        if (count($this->list) == 0) {
            return false;
        }

        foreach ($this->getSubsets($this->list) as $subset) {
            if (count($subset) > 0) {
                if (array_sum($subset) == $value) {
                    return true;
                }
            }
        }

        return false;
    }
}
