package dev.root.baseUi.composeui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.root.baseUi.R

@Composable
fun RepWidgetAppBar(
    title: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    shouldShowBack: Boolean = false,
) {
    RepWidgetAppBar(
        title,
        onBackPressed,
        null,
        null,
        shouldShowBack = shouldShowBack,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepWidgetAppBar(
    title: String,
    onBackPressed: () -> Unit,
    actionIcon: ImageVector? = null,
    @StringRes actionDescriptionId: Int? = null,
    modifier: Modifier = Modifier,
    shouldShowBack: Boolean = false,
    onActionClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        navigationIcon = {
            if (shouldShowBack)
                RepWidgetBackButton(onBackPressed)
            else
                null
        },
        actions = {
            if (null != actionIcon)
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = if (null != actionDescriptionId)
                            stringResource(id = actionDescriptionId)
                        else null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
        },
    )
}

@Composable
fun RepWidgetBackButton(onBackPressed: () -> Unit){
    FilledIconButton(
        onClick = onBackPressed,
        modifier = Modifier.padding(8.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back_button),
            modifier = Modifier.size(14.dp)
        )
    }
}