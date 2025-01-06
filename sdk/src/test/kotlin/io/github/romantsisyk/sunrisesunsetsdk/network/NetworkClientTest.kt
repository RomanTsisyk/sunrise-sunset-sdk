package io.github.romantsisyk.sunrisesunsetsdk.network

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException

class NetworkClientTest {

    private val mockClient = mockk<OkHttpClient>()
    private val mockResponse = mockk<Response>(relaxed = true)
    private val mockResponseBody = mockk<ResponseBody>()

    @Test
    fun `should return response body when request is successful`() {
        val url = "https://example.com/api"
        val expectedResponse = "Success"
        val slot = slot<Request>()

        every { mockClient.newCall(capture(slot)).execute() } returns mockResponse
        every { mockResponse.isSuccessful } returns true
        every { mockResponse.body } returns mockResponseBody
        every { mockResponseBody.string() } returns expectedResponse

        val networkClient = NetworkClient(client = mockClient)

        val result = networkClient.get(url)

        assertEquals(expectedResponse, result)
        assertEquals(url, slot.captured.url.toString())
        verify { mockResponse.close() } // Ensure the response is closed
    }

    @Test
    fun `should throw exception for HTTP error`() {
        val url = "https://example.com/api"
        every { mockClient.newCall(any()).execute() } returns mockResponse
        every { mockResponse.isSuccessful } returns false
        every { mockResponse.code } returns 404
        every { mockResponse.message } returns "Not Found"

        val networkClient = NetworkClient(client = mockClient)

        val exception = assertThrows<Exception> {
            networkClient.get(url)
        }
        assertTrue(exception.message!!.contains("HTTP error code: 404"))
    }

    @Test
    fun `should throw exception for empty response body`() {
        val url = "https://example.com/api"
        every { mockClient.newCall(any()).execute() } returns mockResponse
        every { mockResponse.isSuccessful } returns true
        every { mockResponse.body } returns null

        val networkClient = NetworkClient(client = mockClient)

        val exception = assertThrows<Exception> {
            networkClient.get(url)
        }
        assertEquals("Empty response body.", exception.message)
    }

    @Test
    fun `should handle IOException gracefully`() {
        val url = "https://example.com/api"
        every { mockClient.newCall(any()).execute() } throws IOException("Network error")

        val networkClient = NetworkClient(client = mockClient)

        val exception = assertThrows<IOException> {
            networkClient.get(url)
        }
        assertTrue(exception.message!!.contains("Network error"))
    }
}
