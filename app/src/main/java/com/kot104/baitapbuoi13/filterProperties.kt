package com.kot104.baitapbuoi13

import com.kot104.baitapbuoi13.room.Entity.CustomerRequirementsEntity
import com.kot104.baitapbuoi13.room.Entity.PropertyEntity

fun filterProperties(
    properties: List<PropertyEntity>,
    requirements: CustomerRequirementsEntity?
): List<PropertyEntity> {
    if (requirements == null) {
        return properties // Nếu không có yêu cầu, trả về toàn bộ danh sách
    }

    return properties.filter { property ->
        val decisionTree = DecisionNode(
            condition = { requirements.preferredLocation.isNullOrEmpty() ||  property.AddressProperty.contains(requirements.preferredLocation, ignoreCase = true)},
            trueNode = DecisionNode(
                condition = { requirements.propertyType.isNullOrEmpty() ||  property.propertyType.contains(requirements.propertyType, ignoreCase = true)},
                trueNode = DecisionNode(
                    condition = { requirements.budgetMin == null || requirements.budgetMax == null || (property.price >= requirements.budgetMin &&  property.price <= requirements.budgetMax)},
                    trueNode = DecisionNode(
                        condition = { requirements.bedrooms == null || property.bedrooms >= requirements.bedrooms},
                        trueNode = DecisionNode(
                            condition = { requirements.bathrooms == null || property.bathrooms >= requirements.bathrooms},
                            trueNode = DecisionNode(
                                condition = { requirements.sizeMin == null || property.size >= requirements.sizeMin},
                                trueNode = DecisionNode(
                                    condition = { true},  // Base Case. All filter pass
                                    trueNode = null,
                                    falseNode = null
                                ),
                                falseNode = null
                            ),
                            falseNode = null
                        ),
                        falseNode = null
                    ),
                    falseNode = null
                ),
                falseNode = null
            ),
            falseNode = null
        )

        decisionTree.evaluate()
    }
}

// Định nghĩa Node cho cây quyết định
class DecisionNode(
    val condition: () -> Boolean,
    val trueNode: DecisionNode?,
    val falseNode: DecisionNode?
){
    fun evaluate(): Boolean{
        if(condition()){
            return trueNode?.evaluate() ?: true
        }else{
            return falseNode?.evaluate() ?: false
        }
    }
}