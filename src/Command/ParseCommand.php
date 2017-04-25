<?php

namespace DinoSetoSpace\Command;

use DinoSetoSpace\Parser;
use DinoSetoSpace\Tokenizer;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Exception\RuntimeException;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;

class ParseCommand extends Command
{
    protected function configure()
    {
        $this->setName('parse')
            ->setDescription('Parses code from SirenoExpress style to DinoSetoSpace style')
            ->addArgument('filename', null, 'filename to parse');
    }

    protected function execute(InputInterface $input, OutputInterface $output)
    {
        if ($filename = $input->getArgument('filename')) {
            if (!is_file($filename)) {
                throw new RuntimeException("filename '$filename' not exists");
            }

            $source = file_get_contents($filename);

            if (false === $source) {
                throw new RuntimeException("can't open '$filename' for reading");
            }
        } else {
            throw new \RuntimeException("Please provide a filename to parse (--help for help)");
        }

        $tokenizer = new Tokenizer($source);
        $parser = new Parser($tokenizer);

        $output->write($parser->execute());
    }
}
