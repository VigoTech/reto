<?php

namespace SubsetSum;

class DPMatrix
{
    private $list;
    private $matrix;
    private $header;

    public function setList($list)
    {
        $this->list = $list;
        sort($this->list, SORT_NUMERIC);
        return $this;
    }

    private function buildHeader()
    {
        $sumNegatives = 0;
        $sumPositives = 0;
        foreach ($this->list as $element) {
            if ($element > 0) {
                $sumPositives += $element;
            }
            if ($element < 0) {
                $sumNegatives += $element;
            }
        }

        $this->header = range($sumNegatives, $sumPositives);
    }

    private function initialize()
    {
        $this->matrix=[];
        for ($row = 0; $row < count($this->list); $row++) {
            for ($column = 0; $column < count($this->header); $column++) {
                $this->matrix[$row][$column] = 'F';
            }
        }
    }

    private function fillFirstRow()
    {
        foreach ($this->matrix[0] as $column_index => $column_value) {
            if ($this->list[0] == $this->header[$column_index]) {
                $this->matrix[0][$column_index] = 'T';
            }
        }
    }

    private function fillAll()
    {
        foreach ($this->list as $row_index => $row_value) {
            if ($row_index == 0) {
                continue;
            }
            foreach ($this->header as $column_index => $column_value) {
                if (
                    $row_value == $column_value ||
                    $this->matrix[$row_index - 1][$column_index] == 'T' ||
                    $this->currentSumPossible($row_index, $column_index)
                ) {
                    $this->matrix[$row_index][$column_index] = 'T';
                }
            }
        }
    }

    private function currentSumPossible($row_index, $column_index)
    {
        $column_sum = $this->header[$column_index] - $this->list[$row_index];

        $index = array_search($column_sum, $this->header, true);

        if (false === $index) {
            return false;
        }
        return ($this->matrix[$row_index - 1][$index] == 'T') ? true : false;
    }

    public function get()
    {
        return $this->matrix;
    }

    public function existsSum($value = 0)
    {
        if (in_array(0, $this->list)) {
            return true;
        }

        if ($this->list[0] > 0) {
            return false;
        }

        $this->buildHeader();
        $this->initialize();
        $this->fillFirstRow();
        $this->fillAll();
        $column_index_from_value = array_search($value, $this->header, true);

        for ($row = 0; $row < count($this->list); $row++) {
            if ($this->matrix[$row][$column_index_from_value] == 'T') {
                return true;
            }
        }

        return false;
    }
}
