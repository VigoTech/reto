<?php

namespace DinoSetoSpace;

final class Tokens
{
    const TOKEN_IF = 'IF';
    const TOKEN_ENDIF = 'ENDIF';
    const TOKEN_FOR = 'FOR';
    const TOKEN_ENDFOR = 'NEXT';
    const TOKEN_WHITESPACE = ' ';
    const TOKEN_NEWLINE = "\n";

    /**
     * A list of tokens that start a scope.
     *
     * @var array
     */
    public static $startScopeTokens = [
        self::TOKEN_IF => [
            'start' => [],
            'end' => [self::TOKEN_ENDIF]
        ],
        self::TOKEN_FOR => [
            'start' => [],
            'end' => [self::TOKEN_ENDFOR]
        ]
    ];

    /**
     * A list of tokens that end a scope.
     *
     * @var array
     */
    public static $endScopeTokens = [
        self::TOKEN_ENDIF => [
            'start' => [self::TOKEN_IF],
            'end' => []
        ],
        self::TOKEN_ENDFOR => [
            'start' => [self::TOKEN_FOR],
            'end' => []
        ]
    ];

    /**
     * Returns true if $token can start a scope, false otherwise
     *
     * @param $token
     * @return bool
     */
    public static function isStartScopeToken($token)
    {
        return in_array($token, array_keys(self::$startScopeTokens));
    }

    /**
     * Returns true if $token can end a scope, false otherwise
     *
     * @param $token
     * @return bool
     */
    public static function isEndScopeToken($token)
    {
        return in_array($token, array_keys(self::$endScopeTokens));
    }
}
