package store.domain.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import store.data.repository.FakePromotionRepositoryImpl
import store.domain.model.promotion.PromotionItem

class GetInProgressPromotionUseCaseTest {
    private lateinit var getInProgressPromotionUseCase: GetInProgressPromotionUseCase

    @BeforeEach
    fun setUp() {
        val fakePromotionRepository = FakePromotionRepositoryImpl()
        getInProgressPromotionUseCase = GetInProgressPromotionUseCase(fakePromotionRepository)
    }

    @Test
    fun `유효한_날짜의_프로모션_테스트`() {
        val promotion = "탄산2+1"
        val expected = getInProgressPromotionUseCase(promotion)
        assertEquals(
            expected,
            PromotionItem("탄산2+1", 2, 1, "2024-01-01", "2024-12-31")
        )
    }

    @Test
    fun `종료일이_끝난_날짜의_프로모션_테스트`() {
        val promotion = "무료반품"
        val expected = getInProgressPromotionUseCase(promotion)
        assertEquals(expected, null)
    }

    @Test
    fun `시작되지_않은_날짜의_프로모션_테스트`() {
        val promotion = "사장님이미쳤어요"
        val expected = getInProgressPromotionUseCase(promotion)
        assertEquals(expected, null)
    }
}