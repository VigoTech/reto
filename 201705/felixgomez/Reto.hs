module Reto where
import Data.List
getSubsetsSumZero :: (Eq a, Num a) => [a] -> [[a]]
getSubsetsSumZero = filter ((==0) . sum) . subsequences
