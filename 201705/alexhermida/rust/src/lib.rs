#[macro_use]
extern crate itertools;

use itertools::Itertools;


fn check_sum_zero(v: Vec<i32>) -> bool {
    let list_1 = v;

    for i in (1..list_1.len()).rev() {

        let iter = list_1.iter();

        for item in iter.combinations(i) {
            let a = item.into_iter();
            let sum: i32 = a.sum();

            if sum == 0 {
                return true;
            }
        }
    }
    return false;
}


#[cfg(test)]
mod tests {
    use super::check_sum_zero;

    #[test]
    fn it_works() {

        let list_1 = vec![-97364, -71561, -69336, 19675, 71561, 97863];

        assert_eq!(true, check_sum_zero(list_1));

    }

    #[test]
    fn failed() {
        let list_1 = vec![-83314, -82838, -80120, -63468, -62478, -59378, -56958, -50061, -34791, -32264, -21928, -14988, 23767, 24417, 26403, 26511, 36399, 78055];

        assert_eq!(false, check_sum_zero(list_1));

    }
}