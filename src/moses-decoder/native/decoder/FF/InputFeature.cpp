#include <stdexcept>
#include "InputFeature.h"
#include "Util.h"
#include "ScoreComponentCollection.h"
#include "InputPath.h"
#include "StaticData.h"

using namespace std;

namespace Moses
{
InputFeature *InputFeature::s_instance = NULL;

InputFeature::InputFeature(const std::string &line)
  : StatelessFeatureFunction(line)
  , m_numRealWordCount(0)
{
  m_numInputScores = this->m_numScoreComponents;
  ReadParameters();

  UTIL_THROW_IF2(s_instance, "Can only have 1 input feature");
  s_instance = this;
}

void InputFeature::Load(AllOptions::ptr const& opts)
{
  m_options = opts;
  m_legacy = false;
}

void InputFeature::SetParameter(const std::string& key, const std::string& value)
{
  if (key == "num-input-features") {
    m_numInputScores = Scan<size_t>(value);
  } else if (key == "real-word-count") {
    m_numRealWordCount = Scan<size_t>(value);
  } else {
    StatelessFeatureFunction::SetParameter(key, value);
  }

}

void InputFeature::EvaluateWithSourceContext(const InputType &input
    , const InputPath &inputPath
    , const TargetPhrase &targetPhrase
    , const StackVec *stackVec
    , ScoreComponentCollection &scoreBreakdown
    , ScoreComponentCollection *estimatedScores) const
{
  if (m_legacy) {
    //binary phrase-table does input feature itself
    return;
  } else if (input.GetType() == WordLatticeInput) {
    const ScorePair *scores = inputPath.GetInputScore();
    if (scores) {
      scoreBreakdown.PlusEquals(this, *scores);
    }
  }
}

} // namespace

