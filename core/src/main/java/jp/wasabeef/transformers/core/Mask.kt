package jp.wasabeef.transformers.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.annotation.DrawableRes

/**
 * Copyright (C) 2020 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class Mask constructor(
  private val context: Context,
  @DrawableRes private val maskId: Int
) : Transformer() {

  override fun transform(
    source: Bitmap,
    destination: Bitmap
  ): Bitmap {

    destination.density = source.density
    destination.setHasAlpha(true)

    val paint = Paint().apply {
      xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
      isAntiAlias = true
      isFilterBitmap = true
    }

    val canvas = Canvas(destination)
    context.getDrawable(maskId)?.run {
      setBounds(0, 0, source.width, source.height)
      draw(canvas)
    }
    canvas.drawBitmap(
      source,
      0f,
      0f,
      paint
    )
    canvas.setBitmap(null)

    return destination
  }

  override fun key(): String = "$id(maskId=$maskId)"
}