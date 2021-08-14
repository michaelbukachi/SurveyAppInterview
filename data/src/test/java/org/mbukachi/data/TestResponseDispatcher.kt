package org.mbukachi.data

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.QueueDispatcher
import okhttp3.mockwebserver.RecordedRequest

class TestResponseDispatcher : QueueDispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)
        val body = javaClass.classLoader.getResource("response.json").readText()
        mockResponse.setBody(body)
        return mockResponse
    }
}