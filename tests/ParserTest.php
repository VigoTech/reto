<?php

use PHPUnit\Framework\TestCase;

class ParserTest extends TestCase
{
    /**
     * Provides data to test Example1
     *
     * @return array
     */
    public function provideExample1Code()
    {
        return [
            [
                <<<'EXPECTED'
VAR I
FOR I=1 TO 31
····IF !(I MOD 3) THEN
········PRINT "FIZZ"
····ENDIF
····IF !(I MOD 5) THEN
········PRINT "BUZZ"
····ENDIF
····IF (I MOD 3) && (I MOD 5) THEN
········PRINT "FIZZBUZZ"
····ENDIF
NEXT
EXPECTED
                ,
                <<<'INPUT'
12
····
VAR I
·FOR I=1 TO 31
»»»»IF !(I MOD 3) THEN
··PRINT "FIZZ"
··»»ENDIF
»»»»····IF !(I MOD 5) THEN
»»»»··PRINT "BUZZ"
··»»»»»»ENDIF
»»»»IF (I MOD 3) && (I MOD 5) THEN
······PRINT "FIZZBUZZ"
··»»ENDIF
»»»»·NEXT
INPUT
            ]
        ];
    }

    /**
     * Test example1 proposed code
     *
     * @dataProvider provideExample1Code
     * @param $input string
     * @param $expected string
     */
    public function testExample1Code($expected, $input = null)
    {
        $parser = new \DinoSetoSpace\Parser(new \DinoSetoSpace\Tokenizer($input));
        $this->assertEquals($parser->execute(), $expected);
    }

    /**
     * Provides data to test Example2
     *
     * @return array
     */
    public function provideExample2Code()
    {
        return [
            [
                <<<'INPUT'
4
····
FOR I=0 TO 10
····IF I MOD 2 THEN
········PRINT I
NEXT
INPUT
            ]
        ];
    }

    /**
     * Test example2 proposed code
     *
     * @dataProvider provideExample2Code
     * @param $input string
     */
    public function testExample2Code($input = null)
    {
        $parser = new \DinoSetoSpace\Parser(new \DinoSetoSpace\Tokenizer($input));
        $this->expectException(\Symfony\Component\Console\Exception\RuntimeException::class);

        $parser->execute();
    }

    /**
     * Provides data to test Example3
     *
     * @return array
     */
    public function provideExample3Code()
    {
        return [
            [
                <<<'INPUT'
3
····
FOR I=0 TO 10
····IF I MOD 2 THEN
········PRINT I
INPUT
            ]
        ];
    }

    /**
     * Test example3 proposed code
     *
     * @dataProvider provideExample3Code
     * @param $input string
     */
    public function testExample3Code($input = null)
    {
        $parser = new \DinoSetoSpace\Parser(new \DinoSetoSpace\Tokenizer($input));
        $this->expectException(\Symfony\Component\Console\Exception\RuntimeException::class);

        $parser->execute();
    }

    /**
     * Provides data to test Example4
     *
     * @return array
     */
    public function provideExample4Code()
    {
        return [
            [
                <<<'INPUT'
3
····
FOR I=0 TO 10
····PRINT I
ENDIF
INPUT
            ]
        ];
    }

    /**
     * Test example4 proposed code
     *
     * @dataProvider provideExample4Code
     * @param $input string
     */
    public function testExample4Code($input = null)
    {
        $parser = new \DinoSetoSpace\Parser(new \DinoSetoSpace\Tokenizer($input));
        $this->expectException(\Symfony\Component\Console\Exception\RuntimeException::class);

        $parser->execute();
    }

    /**
     * Provides data to test Example5
     *
     * @return array
     */
    public function provideExample5Code()
    {
        return [
            [
                <<<'INPUT'
4
····
FOR I=0 TO 10
····PRINT I
NEXT
ENDIF
INPUT
            ]
        ];
    }

    /**
     * Test example5 proposed code
     *
     * @dataProvider provideExample5Code
     * @param $input string
     */
    public function testExample5Code($input)
    {
        $parser = new \DinoSetoSpace\Parser(new \DinoSetoSpace\Tokenizer($input));
        $this->expectException(\Symfony\Component\Console\Exception\RuntimeException::class);

        $parser->execute();
    }
}
