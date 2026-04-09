package com.example.businesscard.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.platform.LocalUriHandler
import com.example.businesscard.R

@Composable
fun BusinessCardScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    val bg = MaterialTheme.colorScheme.background
    Surface(
        modifier = modifier,
        color = bg
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            val isLandscapeLike = maxWidth > maxHeight
            if (isLandscapeLike) {
                BusinessCardLandscape(
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                BusinessCardPortrait(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun BusinessCardPortrait(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.screen_padding))
    ) {
        BusinessCardHeader(
            modifier = Modifier
                .align(Alignment.Center)
        )

        BusinessCardContacts(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = dimensionResource(R.dimen.screen_padding)),
            align = Alignment.CenterHorizontally,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BusinessCardLandscape(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Row(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(all = dimensionResource(R.dimen.screen_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.section_gap)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BusinessCardHeader()
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                BusinessCardContacts(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    align = Alignment.CenterHorizontally,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
private fun BusinessCardHeader(modifier: Modifier = Modifier) {
    val avatarSize = dimensionResource(R.dimen.avatar_size)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Avatar(
            drawableRes = R.drawable.profile_photo,
            contentDescription = stringResource(R.string.photo_content_description),
            modifier = Modifier.size(avatarSize)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.title_gap)))
        Text(
            text = stringResource(R.string.full_name),
            style = MaterialTheme.typography.headlineMedium,
            color = colorResource(R.color.text_primary),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.subtitle_gap)))
        Text(
            text = stringResource(R.string.group_line),
            style = MaterialTheme.typography.titleMedium,
            color = colorResource(R.color.text_secondary),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BusinessCardContacts(
    modifier: Modifier = Modifier,
    align: Alignment.Horizontal,
    textAlign: TextAlign
) {
    val uriHandler = LocalUriHandler.current
    val githubUrl = stringResource(R.string.github_value)
    val phoneValue = stringResource(R.string.phone_value)
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = when (align) {
            Alignment.Start -> Alignment.CenterStart
            Alignment.End -> Alignment.CenterEnd
            else -> Alignment.Center
        }
    ) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Min),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.contact_row_gap))
        ) {
            ContactRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = stringResource(R.string.email_icon_content_description),
                        modifier = Modifier.size(dimensionResource(R.dimen.contact_icon_size)),
                        tint = colorResource(R.color.icon_tint)
                    )
                },
                text = stringResource(R.string.email_value),
                textAlign = textAlign
            )
            ContactRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = stringResource(R.string.phone_icon_content_description),
                        modifier = Modifier.size(dimensionResource(R.dimen.contact_icon_size)),
                        tint = colorResource(R.color.icon_tint)
                    )
                },
                text = phoneValue,
                textAlign = textAlign,
                onClick = { uriHandler.openUri("tel:$phoneValue") }
            )
            ContactRow(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_github),
                        contentDescription = stringResource(R.string.github_icon_content_description),
                        modifier = Modifier.size(dimensionResource(R.dimen.contact_icon_size)),
                        tint = colorResource(R.color.icon_tint)
                    )
                },
                text = githubUrl,
                textAlign = textAlign,
                onClick = { uriHandler.openUri(githubUrl) }
            )
        }
    }
}

@Composable
private fun ContactRow(
    icon: @Composable () -> Unit,
    text: String,
    textAlign: TextAlign,
    onClick: (() -> Unit)? = null
) {
    val iconSize = dimensionResource(R.dimen.contact_icon_size)
    Row(
        modifier = Modifier
            .then(
                if (onClick != null) {
                    Modifier.selectable(
                        selected = false,
                        onClick = onClick,
                        role = Role.Button
                    )
                } else {
                    Modifier
                }
            )
            .padding(vertical = dimensionResource(R.dimen.contact_row_padding_v))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(iconSize),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.contact_icon_gap)))
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(R.color.text_primary),
            textAlign = textAlign,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun Avatar(
    @DrawableRes drawableRes: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(drawableRes),
        contentDescription = contentDescription,
        modifier = modifier.clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

