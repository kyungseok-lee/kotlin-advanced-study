package study.kotlin.advanced.generic

// declaration-site variance: 소비만 하는 클래스로 분리
class Cage4<in T> {
    private val animals: MutableList<T> = mutableListOf()

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun putAll(animals: List<T>) {
        this.animals.addAll(animals)
    }
}

// declaration-site variance: 생산만 하는 클래스로 분리
class Cage3<out T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun getAll(): List<T> = animals.toList()
}

class Cage2<T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun put(animal: T) {
        animals.add(animal)
    }

    // use-site variance
    fun moveFrom(otherCage: Cage2<out T>) {
        // out 키워드는 생성자 역할만 가능
        // Type 안정성이 깨질 수 있기 때문에

        //otherCage.getFirst() // 허용
        //otherCage.put(this.getFirst()) // out keyword가 있을 경우 값을 넣는 것은 불가

        this.animals.addAll(otherCage.animals)
    }

    // use-site variance
    fun moveTo(otherCage: Cage2<in T>) {
        otherCage.animals.addAll(this.animals)
    }
}

class Cage {
    private val animals: MutableList<Animal> = mutableListOf()

    fun getFirst(): Animal = animals.first()

    fun put(animal: Animal) {
        animals.add(animal)
    }

    fun moveFrom(cage: Cage) {
        this.animals.addAll(cage.animals)
    }
}
