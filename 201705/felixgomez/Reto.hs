module Reto where

createAllSubsets :: [a] -> [[a]]
createAllSubsets [] = [[]]
createAllSubsets (x:xs) = createAllSubsets xs ++ map (x:) (createAllSubsets xs)

getSubsetsSumZero :: (Eq a, Num a) => [a] -> [[a]]
getSubsetsSumZero = filter ((==0) . sum) . createAllSubsets
