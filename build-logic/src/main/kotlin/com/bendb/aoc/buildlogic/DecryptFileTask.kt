package com.bendb.aoc.buildlogic

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.process.CommandLineArgumentProvider

abstract class DecryptFileTask : Exec() {
  @get:InputFile
  abstract val inputFile: RegularFileProperty

  @get:Input
  abstract val passphrase: Property<String>

  @get:OutputFile
  abstract val outputFile: RegularFileProperty

  init {
    argumentProviders.add(StaticArgumentsProvider())
    argumentProviders.add(PassphraseArgumentProvider(passphrase))
    argumentProviders.add(OutputFileArgumentProvider(outputFile))
    argumentProviders.add(InputFileArgumentProvider(inputFile))
  }

  class StaticArgumentsProvider : CommandLineArgumentProvider {
    override fun asArguments(): Iterable<String> {
      return listOf("--decrypt", "--batch", "--yes", "--cipher-algo", "AES256")
    }
  }

  class PassphraseArgumentProvider(
    private val passphrase: Property<String>,
  ) : CommandLineArgumentProvider {
    override fun asArguments(): Iterable<String> {
      return listOf("--passphrase", "'" + passphrase.get() + "'")
    }
  }

  class OutputFileArgumentProvider(
    private val outputFile: RegularFileProperty,
  ) : CommandLineArgumentProvider {
    override fun asArguments(): Iterable<String> {
      return listOf("-o", outputFile.get().asFile.absolutePath)
    }
  }

  class InputFileArgumentProvider(
    private val inputFile: RegularFileProperty,
  ) : CommandLineArgumentProvider {
    override fun asArguments(): Iterable<String> {
      return listOf(inputFile.get().asFile.absolutePath)
    }
  }
}