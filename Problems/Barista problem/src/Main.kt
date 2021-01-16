// write the EspressoMachine class here
class EspressoMachine {
    var costPerServing: Float

    constructor(coffeeCapsulesCount: Int, totalCost: Float) {
        this.costPerServing = totalCost / coffeeCapsulesCount
    }

    constructor(coffeeBeansWeight: Float, totalCost: Float) {
        this.costPerServing = totalCost / (coffeeBeansWeight / 10.6f).toInt()
    }
}