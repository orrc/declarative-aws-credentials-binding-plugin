# AWS Credentials Binding for Declarative Pipelines

This is a minimal Jenkins plugin that automatically exposes separate environment variables for the access key and the secret key, given IAM credentials created using the [AWS Credentials Plugin][0].

## Functionality
Given an IAM credential bound to an environment variable `X`, the environment variables `X_KEY` and `X_SECRET` will be created automatically.

## Examples
Basic usage:

```groovy
stage('Release') {
  environment {
    // Bind an existing IAM credential to the build environment
    SOME_AWS_USER_CREDS = credentials('some-aws-user')
  }
  steps {
    // With this plugin installed, these two environment variables are exposed;
    // giving us the access key, and the secret key from the above credential
    echo "I've got the key:    ${SOME_AWS_USER_CREDS_KEY}"
    echo "I've got the secret: ${SOME_AWS_USER_CREDS_SECRET}"
  }
}
```

Alternatively, you can use this to expose the [canonical AWS environment variable names][1], allowing tools that use the AWS SDK to automatically pick up authentication from the build environment:

```groovy
stage('Release') {
  environment {
    // Bind an existing IAM credential to the build environment
    SOME_AWS_USER_CREDS = credentials('some-aws-user')

    // You can immediately make use of the automatically
    // created environment variables in the same block…
    AWS_ACCESS_KEY_ID = "${SOME_AWS_USER_CREDS_KEY}"
    AWS_SECRET_ACCESS_KEY = "${SOME_AWS_USER_CREDS_SECRET}"
  }
  steps {
    // Magically, any tools using the AWS SDK will work
    sh "aws …"
  }
}
```

## Alternatives
This is an alternative to using the `withAWS` step or option from the [Pipeline: AWS Steps][2] plugin.

[0]:https://plugins.jenkins.io/aws-credentials/
[1]:https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-envvars.html#envvars-list
[2]:https://plugins.jenkins.io/pipeline-aws/
