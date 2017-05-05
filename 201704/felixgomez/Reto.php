#!/usr/bin/env php
<?php

require __DIR__ . '/vendor/autoload.php';

use DinoSetoSpace\Command\ParseCommand;
use Symfony\Component\Console\Application;

$parse = new ParseCommand();

$app = new Application('SirenoExpress to DinoSetoSpace Converter', '1.0');
$app->add($parse);
$app->setDefaultCommand($parse->getName());
$app->run();
