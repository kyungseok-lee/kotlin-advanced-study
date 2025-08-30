# 코틀린 고급편 스터디

인프런 **[코틀린 고급편](https://www.inflearn.com/course/%EC%BD%94%ED%8B%80%EB%A6%B0-%EA%B3%A0%EA%B8%89%ED%8E%B8/dashboard)** 강의를 보며 실습/응용 코드를 작성하는 개인 스터디 저장소입니다.
강의 예제를 그대로 베끼기보다는 **코드를 직접 타이핑하고 변형**하면서 심화 개념을 체화하는 것을 목표로 합니다.

## Generic
### use-site variance
in, out 키워드를 통해 상속 관계에서 Generic Class의 관계 유지

공변 : co-variance, 함수 파라미터 입장에서 out 키워드가 붙을 경우 생산자, 공변 상태
반공변 : contra-variance, 함수 파라미터 입장에서 in 키워드가 붙을 경우 소비자, 반공변 상태

```kotlin
class Cage2<T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun put(animal: T) {
        animals.add(animal)
    }

    fun moveFrom(otherCage: Cage2<out T>) {
        this.animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: Cage2<in T>) {
        otherCage.animals.addAll(this.animals)
    }
}
```

상위 타입에 하위 타입 변수를 넣는 경우 또는 반대의 경우
```kotlin
fun main() {
    val cage1: Cage2<out Fish> = Cage2<GoldFish>()
    val cage2: Cage2<in GoldFish> = Cage2<Fish>()
}
```

### declaration-site variance
클래스 자체를 공변하게 선언
```kotlin
// 소비만 하는 클래스로 분리
class Cage4<in T> {
    private val animals: MutableList<T> = mutableListOf()

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun putAll(animals: List<T>) {
        this.animals.addAll(animals)
    }
}

// 생산만 하는 클래스로 분리
class Cage3<out T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun getAll(): List<T> = animals.toList()
}
```

### 제약 generic constraints
Type에 대한 제약, Animal Type 및 Computable 구현에 대한 예시
```kotlin
class Cage5<T>(
    private val animals: MutableList<T> = mutableListOf()
) where T : Animal, T : Comparable<T> {}
```

### Type의 Nulla에 대한 제약
```kotlin
class Cage6<T : Any>
```

### 제네릭 함수 예시
```kotlin
fun <T> List<T>.hasIntersection(other: List<T>): Boolean {
    return (this.toSet() intersect other.toSet()).isNotEmpty()
}
```

### Type 소개와 Star Projection
java에서 generic 사용 시 type이 유실되기 때문에 kotlin에서는 reified 키워드를 사용해 구현
```kotlin
inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean {
    return this.any { it is T }
}

inline fun <reified T> List<*>.containsType() = this.firstOrNull { it is T } != null
```