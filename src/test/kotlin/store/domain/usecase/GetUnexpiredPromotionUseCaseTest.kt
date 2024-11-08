package store.domain.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import store.data.repository.FakePromotionRepositoryImpl
import store.domain.model.promotion.PromotionItem
import store.domain.model.promotion.Promotions

class GetUnexpiredPromotionUseCaseTest {
    private lateinit var getUnexpiredPromotionUseCase: GetUnexpiredPromotionUseCase

    @BeforeEach
    fun setUp() {
        val fakePromotionRepository = FakePromotionRepositoryImpl()
        getUnexpiredPromotionUseCase = GetUnexpiredPromotionUseCase(fakePromotionRepository)
    }

    @Test
    fun `유효한_날짜의_프로모션_테스트`() {
        val promotion = "탄산2+1"
        val expected = getUnexpiredPromotionUseCase(promotion)
        assertEquals(
            expected,
            Promotions(listOf(
                PromotionItem("탄산2+1", 2, 1, "2024-01-01", "2024-12-31"))
            )
        )
    }

    @Test
    fun `종료일이_끝난_날짜의_프로모션_테스트`() {
        val promotion = "무료반품"
        val expected = getUnexpiredPromotionUseCase(promotion)
        assertEquals(expected, Promotions(listOf()))
    }

    @Test
    fun `시작일이_오늘보다_늦은_날짜의_프로모션_테스트`() {
        val promotion = "사장님이미쳤어요"
        val expected = getUnexpiredPromotionUseCase(promotion)
        assertEquals(expected, Promotions(listOf()))
    }
}