package functional.tests

import grails.plugin.cache.CachePut
import grails.plugin.cache.Cacheable
import grails.plugin.cache.CacheEvict

class CachingService {

	static transactional = false

	private int invocationCounter = 0

	int getInvocationCounter() {
		invocationCounter
	}

	@Cacheable('basic')
	String getData() {
		++invocationCounter
		'Hello World!'
	}

	@Cacheable(value='basic', key='#key')
	def getData(String key) {
	}

	@CachePut(value='basic', key='#key')
	String getData(String key, String value) {
		"** ${value} **"
	}

	@CacheEvict(value='basic', allEntries=true)
	void clear() {}
}
