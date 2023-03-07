package com.dralsoft.inventory.core.data.local

import com.dralsoft.inventory.detail.data.response.InventoryResponse
import com.dralsoft.inventory.list.data.response.*
import kotlinx.coroutines.delay

class InventoryLocalStorage {
    suspend fun listInventory(text: String): ListInventoryResponse {
        delay(500)

        val response = if (text.isNotEmpty()) {

            mockInventoryResponse().data?.filter { it.attributes.name.contains(text, true) }.let {
                ListInventoryResponse(
                    it?.toMutableList(),
                    Meta(Pagination(1, 1, 10, 20))
                )
            }
        } else {
            mockInventoryResponse()
        }

        return response
    }

    suspend fun listInventoryNull(text: String): ListInventoryResponse {
        delay(500)

        return ListInventoryResponse(
            null,
            Meta(Pagination(1, 1, 10, 20))
        )
    }

    suspend fun delete(id: Long): InventoryResponse {
        delay(500)
        return (InventoryResponse(mockInventoryResponse().data?.first { it.id == id }))
    }

    suspend fun getInventory(id: Long): InventoryResponse {
        delay(500)
        return InventoryResponse(
            mockInventoryResponse().data?.find { it.id == id }!!
        )
    }

    suspend fun saveInventory(item: InventoryItem): InventoryResponse {
        delay(500)
        return InventoryResponse(item)
    }
}

fun mockNullInventoryResponse(): ListInventoryResponse {
    return ListInventoryResponse(
        null, Meta(Pagination(1, 1, 10, 20))
    )
}

fun mockInventoryResponse(): ListInventoryResponse {
    return ListInventoryResponse(
        listOf(
            InventoryItem(
                1,
                InventoryAttributes(
                    "Relay 5V",
                    "description",
                    6,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://static.cytron.io/image/cache/catalog/products/BB-RELAY-5V-01/BB-RELAY-5V-01-800x800.jpg",
                        "https://http2.mlstatic.com/D_NQ_NP_852723-MLA52961083957_122022-O.webp"
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                )
            ),
            InventoryItem(
                4,
                InventoryAttributes(
                    "Relay 3V",
                    "description",
                    3,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://www.embtronik.com/wp-content/uploads/2020/01/1-Channel-5V-Relay-Module-Shield-for-1.jpg"
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                ),
            ),
            InventoryItem(
                5,
                InventoryAttributes(
                    "LEDS SMD",
                    "description",
                    1,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://ae01.alicdn.com/kf/S3eb1a79d3ee94681813c0720fdbdce05D/100-Uds-LED-3528-SMD-Blanco-blanco-c-lido-1210-SMD-3528-LED-Ultra-brillante-blanco.jpg_Q90.jpg_.webp"
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                )
            ),
            InventoryItem(
                6,
                InventoryAttributes(
                    "Motor paso a paso",
                    "De alto torque de 40MM, 2 fases, 4 conductores, Nema17, 42BYGH40, 40MM, 1.7A, 0.45N.M, bajo ruido (17HS2401) para CNC XYZ",
                    14,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://ae01.alicdn.com/kf/HTB14HsPQ3TqK1RjSZPhq6xfOFXae/Motor-paso-a-paso-42-De-alto-torque-de-40MM-2-fases-4-conductores-Nema17-42BYGH40.jpg_Q90.jpg_.webp",
                        "https://ae01.alicdn.com/kf/HTB1PRg1QZbpK1RjSZFyq6x_qFXaw/Motor-paso-a-paso-42-De-alto-torque-de-40MM-2-fases-4-conductores-Nema17-42BYGH40.jpg_Q90.jpg_.webp",
                        "https://ae01.alicdn.com/kf/HTB1zSwQQ3DqK1RjSZSyq6yxEVXaT/Motor-paso-a-paso-42-De-alto-torque-de-40MM-2-fases-4-conductores-Nema17-42BYGH40.jpg_Q90.jpg_.webp"
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                )
            ),
            InventoryItem(
                7,
                InventoryAttributes(
                    "Mini interruptor de botón",
                    "Mini interruptor de botón impermeable momentáneo, bocina de 12mm, azul, blanco, verde, rojo, amarillo, negro, 1A, 250V, reinicio automático, 1 ud.",
                    5,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://ae01.alicdn.com/kf/HTB1twz0b8Cw3KVjSZFuq6AAOpXa4/Mini-interruptor-de-bot-n-impermeable-moment-neo-bocina-de-12mm-azul-blanco-verde-rojo-amarillo.jpg_640x640.jpg",
                        "https://ae01.alicdn.com/kf/Heafd0c4b3c6942c69492b7f792858acfI/Mini-interruptor-de-bot-n-impermeable-moment-neo-bocina-de-12mm-azul-blanco-verde-rojo-amarillo.jpg_Q90.jpg_.webp",
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                )
            ),
            InventoryItem(
                8,
                InventoryAttributes(
                    "D1 Mini TYPE-C",
                    "Mini Placa de desarrollo WIFI, placa de 3,3 V con pines, D1 Mini TYPE-C/MICRO ESP8266 ESP-12F CH340G V2 USB D1 Mini, NodeMCU Lua IOT",
                    5,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://ae01.alicdn.com/kf/Sb9b52f58ce32458dac4d5d1ae4d1c62dq/Mini-Placa-de-desarrollo-WIFI-placa-de-3-3-V-con-pines-D1-Mini-TYPE-C.jpg_Q90.jpg_.webp",
                        "https://ae01.alicdn.com/kf/S149b5a3fc0154c398f1941cfd52229713/Mini-Placa-de-desarrollo-WIFI-placa-de-3-3-V-con-pines-D1-Mini-TYPE-C.jpg_Q90.jpg_.webp",
                        "https://ae01.alicdn.com/kf/Sf8c739abec4c4ac1b3c4007950f0be18t/Mini-Placa-de-desarrollo-WIFI-placa-de-3-3-V-con-pines-D1-Mini-TYPE-C.jpg_Q90.jpg_.webp",
                        "https://ae01.alicdn.com/kf/S28fcb66ee94945a1a824936e988f42adU/Placa-de-desarrollo-WIFI-Mini-placa-de-3-3-V-con-pines-ESP8266-ESP-12-CH340G.jpg_Q90.jpg_.webp",
                        "https://ae01.alicdn.com/kf/Sc072f46d1fcc42c0a7d73b9f029d3b89e/Placa-de-desarrollo-WIFI-Mini-placa-de-3-3-V-con-pines-ESP8266-ESP-12-CH340G.jpg_640x640.jpg",
                        "https://ae01.alicdn.com/kf/S71750a354a0b473f81dd009dc6967150f/Placa-de-desarrollo-WIFI-Mini-placa-de-3-3-V-con-pines-ESP8266-ESP-12-CH340G.jpg_640x640.jpg"
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                ),
            ),
            InventoryItem(
                9,
                InventoryAttributes(
                    "DHT22",
                    "Sensor Digital de temperatura y humedad DHT22/AM2302, módulo de Sensor DHT22, Kit Diy para Arduino",
                    5,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://ae01.alicdn.com/kf/Sd1aa3b4882b44e47937a6e4896d1208cO/Sensor-Digital-de-temperatura-y-humedad-DHT22-AM2302-m-dulo-de-Sensor-DHT22-Kit-Diy-para.jpg_Q90.jpg_.webp"
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                ),
            ), InventoryItem(
                10,
                InventoryAttributes(
                    "Luz infrarroja",
                    "Luz infrarroja de 90 grados para cámaras de vigilancia, tablero de luz LED IR de 10 granos para cámaras de visión nocturna, diámetro de 30mm",
                    5,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://ae01.alicdn.com/kf/HTB18mJJj8smBKNjSZFFq6AT9VXah/Luz-infrarroja-de-90-grados-para-c-maras-de-vigilancia-tablero-de-luz-LED-IR-de.jpg_Q90.jpg_.webp",
                        "https://ae01.alicdn.com/kf/HTB1yM5FtiOYBuNjSsD4q6zSkFXao/Luz-infrarroja-de-90-grados-para-c-maras-de-vigilancia-tablero-de-luz-LED-IR-de.jpg_Q90.jpg_.webp"
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                ),
            ), InventoryItem(
                11,
                InventoryAttributes(
                    "Disipador de calor",
                    "Disipador de calor de aluminio para refrigeración, radiador para Chip IC electrónico, RAM MOS Dynatron, Raspberry Pi, con cinta conductora térmica",
                    5,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://ae01.alicdn.com/kf/S0ea0a2531e39402692569145d1a28e47d/Disipador-de-calor-de-aluminio-para-refrigeraci-n-radiador-para-Chip-IC-electr-nico-RAM-MOS.jpg_640x640.jpg",
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                ),
            ), InventoryItem(
                12,
                InventoryAttributes(
                    "Panel solar",
                    "Mini Panel Solar de 1/2 piezas, placa de polisilicio para exteriores, cargador Solar para cargador de batería de teléfono, 3/2/1/0.4W, 12/6/3/1.5V",
                    5,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://ae01.alicdn.com/kf/S39d1338cdc0c4412b837d4511408bfc0m/Mini-Panel-Solar-de-1-2-piezas-placa-de-polisilicio-para-exteriores-cargador-Solar-para-cargador.jpg_Q90.jpg_.webp",
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                ),
            ), InventoryItem(
                13,
                InventoryAttributes(
                    "Conector USB 3,1 tipo C de 24 Pines",
                    "Conector USB 3,1 tipo C de 24 Pines, adaptador de receptáculo de enchufe macho/hembra para soldar Cable y Cable de 24 Pines, compatible con placa PCB, 10 Uds.",
                    5,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://ae01.alicdn.com/kf/Hb9a89f0d9040438f807a139aa543d9743/Conector-USB-3-1-tipo-C-de-24-Pines-adaptador-de-recept-culo-de-enchufe-macho.jpg_Q90.jpg_.webp",
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                ),
            ), InventoryItem(
                14,
                InventoryAttributes(
                    "HC-SR501",
                    "HC-SR501-Sensor de movimiento infrarrojo piroeléctrico, módulo Detector para Arduino, HC-SR505, AM312, SR602, HW-MS03",
                    5,
                    "Cajonera dentro de Armario",
                    1,
                    listOf(
                        "https://ae01.alicdn.com/kf/Ha7d07c10ea95422fb8ea0339ee9380c6e/HC-SR501-Sensor-de-movimiento-infrarrojo-piroel-ctrico-m-dulo-Detector-para-Arduino-HC-SR505-AM312.jpg_640x640.jpg",
                    ),
                    "OK",
                    "2021-01-01T00:00:00.000Z"
                ),
            )
        ),
        Meta(Pagination(1, 1, 10, 20))
    )
}