#!/usr/bin/env php
<?php

require __DIR__.'/../vendor/autoload.php';

use Symfony\Component\Console\Application;

$application = new Application();


$command = new \VigoJugCoins\Command\CalculateChangeCommand();
$application->add($command);

$application->run();