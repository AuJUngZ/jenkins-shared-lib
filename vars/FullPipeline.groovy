
def callable(Closure... stepsList) {
    pipeline {
        agent any

        stages {
            stage('Dynamic Steps') {
                steps {
                    script {
                        def ctx = [:] // shared context if needed

                        stepsList.eachWithIndex { stepFn, idx ->
                            echo "🔧 Running step ${idx + 1}"

                            try {
                                stepFn(ctx) // pass shared context if needed
                                echo "✅ Step ${idx + 1} succeeded"
                            } catch (err) {
                                echo "❌ Step ${idx + 1} failed: ${err}"
                                slackNotify("Pipeline failed at step ${idx + 1}: ${err.message}")
                                error('Stopping pipeline at failed step.')
                            }
                        }
                    }
                }
            }
        }
    }
}
