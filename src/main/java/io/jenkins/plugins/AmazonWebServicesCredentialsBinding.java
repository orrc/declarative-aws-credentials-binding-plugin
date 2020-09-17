package io.jenkins.plugins;

import com.cloudbees.jenkins.plugins.awscredentials.AmazonWebServicesCredentials;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import hudson.Extension;
import org.jenkinsci.plugins.credentialsbinding.MultiBinding;
import org.jenkinsci.plugins.pipeline.modeldefinition.model.CredentialsBindingHandler;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Extension
public class AmazonWebServicesCredentialsBinding extends CredentialsBindingHandler<AmazonWebServicesCredentials> {

    @Nonnull
    @Override
    public List<MultiBinding<AmazonWebServicesCredentials>> toBindings(String varName, String credentialsId) {
        com.cloudbees.jenkins.plugins.awscredentials.AmazonWebServicesCredentialsBinding binding =
                new com.cloudbees.jenkins.plugins.awscredentials.AmazonWebServicesCredentialsBinding(varName + "_KEY", varName + "_SECRET", credentialsId);
        return Collections.singletonList(binding);
    }

    @Nonnull
    @Override
    public Class<? extends StandardCredentials> type() {
        return AmazonWebServicesCredentials.class;
    }

    @Nonnull
    @Override
    public List<Map<String, Object>> getWithCredentialsParameters(String credentialsId) {
        Map<String, Object> map = new HashMap<>();
        map.put("$class", com.cloudbees.jenkins.plugins.awscredentials.AmazonWebServicesCredentialsBinding.class.getName());
        map.put("accessKeyVariable", new EnvVarResolver("%s_KEY"));
        map.put("secretKeyVariable", new EnvVarResolver("%s_SECRET"));
        map.put("credentialsId", credentialsId);
        return Collections.singletonList(map);
    }

}