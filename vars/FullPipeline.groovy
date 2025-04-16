def call(Closure... stepsList) {
    pipeline {
        agent any

        stages {
            stage('Initialize Context') {
                steps {
                    script {
                        ctx = [:]  // empty map to store state
                        ctx.name = 'Nattapon' // just an example
                    }
                }
            }

            stepsList.eachWithIndex { stepFn, idx ->
                stage("Step ${idx + 1}") {
                    steps {
                        script {
                            try {
                                echo "🔧 Running step ${idx + 1}"
                                stepFn.call(ctx)
                                echo "✅ Step ${idx + 1} succeeded"
                            } catch (err) {
                                echo "❌ Step ${idx + 1} failed: ${err}"
                                error('Stopping pipeline at failed step.')
                            }
                        }
                    }
                }
            }
        }
    }
}
