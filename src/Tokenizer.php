<?php

namespace DinoSetoSpace;

class Tokenizer
{
    /**
     * @var string
     */
    private $source;

    /**
     * @var string
     */
    private $indentString;

    /**
     * @var integer
     */
    private $numberOfLines;

    /**
     * Tokenizer constructor.
     * @param $source
     */
    public function __construct($source)
    {
        $this->parseSource($source);
    }

    /**
     * @param $source string
     */
    public function parseSource($source)
    {
        $lines = explode(Tokens::TOKEN_NEWLINE, $source);
        $this->numberOfLines = intval($lines[0]);
        $this->indentString = $lines[1];

        unset($lines[0]);
        unset($lines[1]);

        array_walk($lines, function (&$line) {
            $line = ltrim($line, '·»');
        });

        $this->source = $lines;
    }

    /**
     * @return integer
     */
    public function getLinesNumber()
    {
        return $this->numberOfLines;
    }

    /**
     * @return string
     */
    public function getIndentCode()
    {
        return $this->indentString;
    }

    /**
     * @return \Generator
     */
    public function getToken()
    {
        foreach ($this->source as $line) {
            foreach (preg_split("/( +)/", $line, -1, PREG_SPLIT_DELIM_CAPTURE) as $token) {
                yield $token;
            }
            yield Tokens::TOKEN_NEWLINE;
        }
    }
}
