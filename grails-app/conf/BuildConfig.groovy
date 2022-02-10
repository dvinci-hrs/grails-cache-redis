//if (System.getenv('TRAVIS_BRANCH')) {
//    grails.project.repos.grailsCentral.username = System.getenv("GRAILS_CENTRAL_USERNAME")
//    grails.project.repos.grailsCentral.password = System.getenv("GRAILS_CENTRAL_PASSWORD")
//}

grails.project.work.dir = 'target'
grails.project.docs.output.dir = 'docs/manual' // for gh-pages branch
grails.project.source.level = 1.8
grails.project.target.level = 1.8

// add repository information to deploy it to our artifactory
// determine snapshot version
boolean isSnapshot = false
new File("${basedir}").eachFile {
    if (it.name.endsWith("GrailsPlugin.groovy")) {
        isSnapshot = it.readLines().any { it.contains("version") && it.contains('SNAPSHOT') }
    }
}

//Nexus Configuration
grails.project.repos.default = ""
grails.project.repos.dvinci.url = ""
grails.project.repos.dvinci.username = ""
grails.project.repos.dvinci.password = ""

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        compile 'redis.clients:jedis:2.7.3'
        compile "org.springframework:spring-expression:$springVersion"
        compile 'org.springframework.data:spring-data-redis:1.6.1.RELEASE', {
            exclude group: 'org.springframework', name: 'spring-aop'
            exclude group: 'org.springframework', name: 'spring-context-support'
            exclude group: 'org.springframework', name: 'spring-context'
        }
        compile 'org.apache.maven.wagon:wagon-http:1.0-beta-2'
    }

    plugins {
        build(':release:3.1.3', ':rest-client-builder:2.1.1') {
            export = false
        }
        compile ':cache:1.1.8'
    }
}
