package innovitics.azimut.models.kyc;

import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;

@NamedEntityGraph(
name = "Question.details",
attributeNodes = {
@NamedAttributeNode("answers"),
@NamedAttributeNode(value="subQuestions"),
@NamedAttributeNode(value="reviews")
},
subgraphs = 
{
@NamedSubgraph(name = "Answer.details",attributeNodes = {@NamedAttributeNode(value="relatedAnswers")}),
@NamedSubgraph(name = "Review.details",attributeNodes = {@NamedAttributeNode("id"),@NamedAttributeNode("result"),@NamedAttributeNode("actionMaker"),@NamedAttributeNode("comment"),@NamedAttributeNode("userId")})
}
)

public class ChildQuestion extends Question{

}
