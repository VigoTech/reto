<?php

namespace DinoSetoSpace;

use Symfony\Component\Console\Exception\RuntimeException;

class Parser
{
    private $tokenizer;
    private $output;
    private $scopeTokens;
    private $newLine;

    /**
     * Parser constructor.
     * @param Tokenizer $tokenizer
     */
    public function __construct(Tokenizer $tokenizer)
    {
        $this->tokenizer = $tokenizer;
        $this->scopeTokens = [];
        $this->newLine = false;
    }

    /**
     * Parses all tokens from tokenizer
     *
     * @return string
     */
    public function execute()
    {
        foreach ($this->tokenizer->getToken() as $token) {
            $this->addToken($token);
        }

        if (count($this->scopeTokens) > 0) {
            throw new RuntimeException("Found " . implode(", ", $this->scopeTokens) . " without opening");
        }

        return trim($this->output);
    }

    /**
     * @param $token
     */
    public function addToken($token)
    {
        if ($token == Tokens::TOKEN_NEWLINE) {
            $this->output .= $token;
            $this->newLine = true;
            return;
        }

        if (Tokens::isEndScopeToken($token)) {
            $lastOpenScopeToken = array_pop($this->scopeTokens);

            if (!$lastOpenScopeToken) {
                throw new RuntimeException("Found $token without opening");
            }

            if (!in_array($lastOpenScopeToken, Tokens::$endScopeTokens[$token]['start'])) {
                throw new RuntimeException("Error: $lastOpenScopeToken found but closed incorrectly ('$token' found instead)");
            }

            if (!in_array($token, Tokens::$startScopeTokens[$lastOpenScopeToken]['end'])) {
                throw new RuntimeException("Error: $token found but closed incorrectly ('$lastOpenScopeToken' found instead)");
            }
        }

        if ($this->newLine) {
            $this->output .= $this->getIndent(count($this->scopeTokens));
            $this->newLine = false;
        }

        if (Tokens::isStartScopeToken($token)) {
            $this->scopeTokens[] = $token;
        }

        $this->output .= $token;
    }

    /**
     * Get indent text from scope $level
     *
     * @param $level integer Scope level
     * @return string Text to indent
     */
    public function getIndent($level)
    {
        return str_repeat($this->tokenizer->getIndentCode(), $level);
    }
}
