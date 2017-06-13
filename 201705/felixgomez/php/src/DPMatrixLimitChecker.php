<?php

namespace SubsetSum;

require __DIR__ . '/../vendor/autoload.php';

class DPMatrixLimitChecker
{
    private $instance;

    public function __construct()
    {
        $this->instance = new DPMatrix();
    }

    public function getCase($n)
    {
        $i = 0;
        $case = [];
        do {
            $case[] = random_int(-500, 500);
            $i++;
        } while ($i < $n);
        return $case;
    }

    public function check()
    {
        $case_number = 50;
        do {
            echo "Case #$case_number\n";
            $list = $this->getCase($case_number);
            echo '[' . implode(",", $list) . ']' . PHP_EOL;
            $initTime = microtime(true);
            if ($this->instance->setList($list)->existsSum()) {
                echo "CASE: OK\n";
            }
            $endTime = microtime(true);
            $elapsedTime = $endTime - $initTime;
            $case_number++;
            echo "Elapsed time: " . $elapsedTime . " (s)\n";
            echo "-----------\n";
        } while ($elapsedTime < 300);
    }
}

(new DPMatrixLimitChecker())->check();
