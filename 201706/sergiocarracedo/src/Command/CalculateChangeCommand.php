<?php

  namespace VigoJugCoins\Command;


  use Symfony\Component\Console\Command\Command;
  use Symfony\Component\Console\Input\InputArgument;
  use Symfony\Component\Console\Input\InputInterface;
  use Symfony\Component\Console\Input\InputOption;
  use Symfony\Component\Console\Output\OutputInterface;
  use VigoJugCoins\Change;


  class CalculateChangeCommand extends Command {
    protected function configure() {
      $this
        ->setName('change')
        ->setDescription('Calculate change')
        ->setHelp('Usage:')
        ->addArgument('change',InputArgument::REQUIRED, 'Change to calculate')
        ->addArgument('currency-coins', InputArgument::REQUIRED, 'Facial values for currency coins (comma separated)')
        ->addOption('coins-amount', 'ca', InputOption::VALUE_OPTIONAL, 'Coins amount each value (comma separated)', '');

    }

    protected function execute(InputInterface $input, OutputInterface $output)
    {

      $parseValue = function($value) {
        return (int)trim($value);
      };

      $coinsValues = explode(',', $input->getArgument('currency-coins'));
      array_walk($coinsValues, $parseValue);

      $coinsAmount = $input->getOption('coins-amount');

      $coinsAmount = !empty($coinsAmount) ? (explode(',', $input->getOption('coins-amount'))) : null;

      if (empty($coinsAmount)) {
        $coinsAmount = array_fill(0, count($coinsValues), '-1');
      }
      array_walk($coinsAmount, $parseValue);

      if (count($coinsValues) != count($coinsAmount)) {
        throw new \Exception('The number of items in <b>currency-coins</b> and <b>coins-amount</b> not the same');
      }

      $output->writeln([
        '<info>Calculating change.....</info>',
        '',
      ]);

      $change = new Change($coinsValues, $coinsAmount);

      $changeArray = $change->getChange($input->getArgument('change'));


      if (!$changeArray) {
        $output->writeln([
          '',
          "<error>Sorry, not enough coins :(</error>",
          '',
        ]);

      } else {
        foreach ($changeArray as $value => $amount) {
          if (!empty($value)) {
            $output->writeln("<info>{$amount}</info>x<comment>{$value}</comment>");
          }
        }

        $output->writeln([
          '',
          "<fg=black;options=blink;bg=yellow;>Don't forget your coins</>",
          '',
        ]);
      }
    }
  }