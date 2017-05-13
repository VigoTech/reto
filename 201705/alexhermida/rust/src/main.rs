#[macro_use]
extern crate itertools;

use itertools::Itertools;


fn main() {

    let list_1 = vec![-97364, -71561, -69336, 19675, 71561, 97863];
    println!("List: {:?}", list_1);

    for i in (1..list_1.len()).rev() {
        println!("Element: {:?}", i);

        let iter = list_1.iter();

        for item in iter.combinations(i) {
            println!("itemm {:?}", item);
            let a = item.into_iter();
            let sum: i32 = a.sum();
            println!("itemm sum {:?}", sum);


            if sum == 0 {
                 println!("entraaaaaaaa");
            }
        }

    }
}