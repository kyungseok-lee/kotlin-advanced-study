package study.kotlin.advanced.generic

fun main() {
    example1()
    example2()
    example3()
    example4()
    example5()
    example6()
}

// in, out을 사용하지 않고 상속 관계를 정의
fun example6() {
    val fishCage = Cage3<Fish>()
    val animalCage: Cage3<Animal> = fishCage

    //animalCage.put(참새) // 불가

}

fun example5() {
    // Initializer type mismatch: expected 'Cage2<Fish>', actual 'Cage2<GoldFish>'.
    val cage1: Cage2<out Fish> = Cage2<GoldFish>()
    val cage2: Cage2<in GoldFish> = Cage2<Fish>()
}

fun example4() {
    val myCage = Cage2<Fish>()

    val goldFishCage = Cage2<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))

    // Argument type mismatch: actual type is 'Cage2<Fish>', but 'Cage2<GoldFish>' was expected.
    //goldFishCage.moveTo(myCage)

    /*
        // 함수 파라미터 입장에서
        // in 키워드가 붙을 경우 소비자, 반공변 상태
        fun moveTo(otherCage: Cage2<in T>) {
            otherCage.animals.addAll(this.animals)
        }
    */
    goldFishCage.moveTo(myCage)
}

// 무공변
fun example3() {
    // Fish와 GoldFish 간의 무공변으로 인한 moveFrom 호출 시의 오류
    val goldFishCage = Cage2<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))

    val fishCage = Cage2<Fish>()

    // Argument type mismatch: actual type is 'Cage2<GoldFish>', but 'Cage2<Fish>' was expected.
    //fishCage.moveFrom(goldFishCage)

    /*
        // 함수 파라미터 입장에서
        // out 키워드가 붙을 경우 생산자, 공변 상태
        fun moveFrom(otherCage: Cage2<out T>) {
            // out 키워드는 생성자 역할만 가능
            // Type 안정성이 깨질 수 있기 때문에

            //otherCage.getFirst() // 허용
            //otherCage.put(this.getFirst()) // out keyword가 있을 경우 값을 넣는 것은 불가

            this.animals.addAll(otherCage.animals)
        }
    */
    fishCage.moveFrom(goldFishCage)

    val fish = fishCage.getFirst()
    println(fish.name)
}

fun example2() {
    // Cage2 Class Generic Type 추가
    val cage = Cage2<Carp>()
    cage.put(Carp("잉어"))

    val carp = cage.getFirst()
    println(carp.name)
}

fun example1() {
    val cage = Cage()
    cage.put(Carp("잉어"))

    // Initializer type mismatch: expected 'Carp', actual 'Animal'.
    //val carp: Carp = cage.getFirst()

    // 대안
    val carp: Carp = cage.getFirst() as? Carp
        ?: throw IllegalStateException("No carp in the cage")

    println(carp.name)
}
