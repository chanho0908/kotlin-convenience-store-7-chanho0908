package store.domain.usecase

import store.domain.model.Exception

class ValidateYesNoInputUseCase {
    operator fun invoke(input: String) {
        require(input.isNotBlank()) { Exception.INVALID_INPUT }
        require(input.trim().length == 1) { Exception.INVALID_INPUT }
        require(input.trim().equals("Y", ignoreCase = true)
                || input.trim().equals("N", ignoreCase = true)
        ) { Exception.INVALID_INPUT }
    }
}
